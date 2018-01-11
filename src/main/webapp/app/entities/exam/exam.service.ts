import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Exam } from './exam.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ExamService {

    private resourceUrl = SERVER_API_URL + 'api/exams';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/exams';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(exam: Exam): Observable<Exam> {
        const copy = this.convert(exam);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(exam: Exam): Observable<Exam> {
        const copy = this.convert(exam);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Exam> {
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
     * Convert a returned JSON object to Exam.
     */
    private convertItemFromServer(json: any): Exam {
        const entity: Exam = Object.assign(new Exam(), json);
        entity.time = this.dateUtils
            .convertDateTimeFromServer(json.time);
        return entity;
    }

    /**
     * Convert a Exam to a JSON which can be sent to the server.
     */
    private convert(exam: Exam): Exam {
        const copy: Exam = Object.assign({}, exam);

        copy.time = this.dateUtils.toDate(exam.time);
        return copy;
    }
}
