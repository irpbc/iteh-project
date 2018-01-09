import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { SchoolYear } from './school-year.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SchoolYearService {

    private resourceUrl = SERVER_API_URL + 'api/school-years';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/school-years';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(schoolYear: SchoolYear): Observable<SchoolYear> {
        const copy = this.convert(schoolYear);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(schoolYear: SchoolYear): Observable<SchoolYear> {
        const copy = this.convert(schoolYear);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<SchoolYear> {
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
     * Convert a returned JSON object to SchoolYear.
     */
    private convertItemFromServer(json: any): SchoolYear {
        const entity: SchoolYear = Object.assign(new SchoolYear(), json);
        entity.startDate = this.dateUtils
            .convertLocalDateFromServer(json.startDate);
        entity.endDate = this.dateUtils
            .convertLocalDateFromServer(json.endDate);
        return entity;
    }

    /**
     * Convert a SchoolYear to a JSON which can be sent to the server.
     */
    private convert(schoolYear: SchoolYear): SchoolYear {
        const copy: SchoolYear = Object.assign({}, schoolYear);
        copy.startDate = this.dateUtils
            .convertLocalDateToServer(schoolYear.startDate);
        copy.endDate = this.dateUtils
            .convertLocalDateToServer(schoolYear.endDate);
        return copy;
    }
}
