import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { OptionalCourseGroup } from './optional-course-group.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class OptionalCourseGroupService {

    private resourceUrl = SERVER_API_URL + 'api/optional-course-groups';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/optional-course-groups';

    constructor(private http: Http) { }

    create(optionalCourseGroup: OptionalCourseGroup): Observable<OptionalCourseGroup> {
        const copy = this.convert(optionalCourseGroup);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(optionalCourseGroup: OptionalCourseGroup): Observable<OptionalCourseGroup> {
        const copy = this.convert(optionalCourseGroup);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<OptionalCourseGroup> {
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
     * Convert a returned JSON object to OptionalCourseGroup.
     */
    private convertItemFromServer(json: any): OptionalCourseGroup {
        const entity: OptionalCourseGroup = Object.assign(new OptionalCourseGroup(), json);
        return entity;
    }

    /**
     * Convert a OptionalCourseGroup to a JSON which can be sent to the server.
     */
    private convert(optionalCourseGroup: OptionalCourseGroup): OptionalCourseGroup {
        const copy: OptionalCourseGroup = Object.assign({}, optionalCourseGroup);
        return copy;
    }
}
