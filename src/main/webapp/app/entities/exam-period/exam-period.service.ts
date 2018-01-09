import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ExamPeriod } from './exam-period.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ExamPeriodService {

    private resourceUrl = SERVER_API_URL + 'api/exam-periods';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/exam-periods';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(examPeriod: ExamPeriod): Observable<ExamPeriod> {
        const copy = this.convert(examPeriod);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(examPeriod: ExamPeriod): Observable<ExamPeriod> {
        const copy = this.convert(examPeriod);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<ExamPeriod> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to ExamPeriod.
     */
    private convertItemFromServer(json: any): ExamPeriod {
        const entity: ExamPeriod = Object.assign(new ExamPeriod(), json);
        entity.startDate = this.dateUtils
            .convertLocalDateFromServer(json.startDate);
        entity.endDate = this.dateUtils
            .convertLocalDateFromServer(json.endDate);
        return entity;
    }

    /**
     * Convert a ExamPeriod to a JSON which can be sent to the server.
     */
    private convert(examPeriod: ExamPeriod): ExamPeriod {
        const copy: ExamPeriod = Object.assign({}, examPeriod);
        copy.startDate = this.dateUtils
            .convertLocalDateToServer(examPeriod.startDate);
        copy.endDate = this.dateUtils
            .convertLocalDateToServer(examPeriod.endDate);
        return copy;
    }
}
