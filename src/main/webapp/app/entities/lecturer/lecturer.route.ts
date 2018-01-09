import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { LecturerComponent } from './lecturer.component';
import { LecturerDetailComponent } from './lecturer-detail.component';
import { LecturerPopupComponent } from './lecturer-dialog.component';
import { LecturerDeletePopupComponent } from './lecturer-delete-dialog.component';

@Injectable()
export class LecturerResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const lecturerRoute: Routes = [
    {
        path: 'lecturer',
        component: LecturerComponent,
        resolve: {
            'pagingParams': LecturerResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.lecturer.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'lecturer/:id',
        component: LecturerDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.lecturer.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lecturerPopupRoute: Routes = [
    {
        path: 'lecturer-new',
        component: LecturerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.lecturer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'lecturer/:id/edit',
        component: LecturerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.lecturer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'lecturer/:id/delete',
        component: LecturerDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.lecturer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
