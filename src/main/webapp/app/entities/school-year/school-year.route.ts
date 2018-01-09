import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { SchoolYearComponent } from './school-year.component';
import { SchoolYearDetailComponent } from './school-year-detail.component';
import { SchoolYearPopupComponent } from './school-year-dialog.component';
import { SchoolYearDeletePopupComponent } from './school-year-delete-dialog.component';

@Injectable()
export class SchoolYearResolvePagingParams implements Resolve<any> {

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

export const schoolYearRoute: Routes = [
    {
        path: 'school-year',
        component: SchoolYearComponent,
        resolve: {
            'pagingParams': SchoolYearResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.schoolYear.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'school-year/:id',
        component: SchoolYearDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.schoolYear.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const schoolYearPopupRoute: Routes = [
    {
        path: 'school-year-new',
        component: SchoolYearPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.schoolYear.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'school-year/:id/edit',
        component: SchoolYearPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.schoolYear.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'school-year/:id/delete',
        component: SchoolYearDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.schoolYear.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
