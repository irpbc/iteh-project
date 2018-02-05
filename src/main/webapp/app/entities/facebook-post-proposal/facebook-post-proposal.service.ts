import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { FacebookPostProposal } from './facebook-post-proposal.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class FacebookPostProposalService {

    private resourceUrl = SERVER_API_URL + 'api/facebook-post-proposals';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(facebookPostProposal: FacebookPostProposal): Observable<FacebookPostProposal> {
        const copy = this.convert(facebookPostProposal);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(facebookPostProposal: FacebookPostProposal): Observable<FacebookPostProposal> {
        const copy = this.convert(facebookPostProposal);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<FacebookPostProposal> {
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

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to FacebookPostProposal.
     */
    private convertItemFromServer(json: any): FacebookPostProposal {
        const entity: FacebookPostProposal = Object.assign(new FacebookPostProposal(), json);
        entity.time = this.dateUtils
            .convertDateTimeFromServer(json.time);
        return entity;
    }

    /**
     * Convert a FacebookPostProposal to a JSON which can be sent to the server.
     */
    private convert(facebookPostProposal: FacebookPostProposal): FacebookPostProposal {
        const copy: FacebookPostProposal = Object.assign({}, facebookPostProposal);

        copy.time = this.dateUtils.toDate(facebookPostProposal.time);
        return copy;
    }
}
