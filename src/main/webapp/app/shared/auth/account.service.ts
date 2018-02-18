import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';
import { User } from '../';

@Injectable()
export class AccountService  {
    constructor(private http: Http) { }

    get(): Observable<User> {
        return this.http.get(SERVER_API_URL + 'api/account').map((res: Response) => res.json());
    }

    save(account: User): Observable<Response> {
        return this.http.post(SERVER_API_URL + 'api/account', account);
    }
}
