import { BaseEntity } from '../model/base-entity';
import { Observable } from 'rxjs/Observable';
import { Response } from '@angular/http';

export interface EntityService<T extends BaseEntity> {

    find(id: number): Observable<T>;

    create(object: T): Observable<T>

    update(object: T): Observable<T>;

    delete(id: number): Observable<Response>;
}
