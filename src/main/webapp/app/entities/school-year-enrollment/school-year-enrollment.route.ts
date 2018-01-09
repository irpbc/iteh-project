import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { SchoolYearEnrollmentComponent } from './school-year-enrollment.component';
import { SchoolYearEnrollmentDetailComponent } from './school-year-enrollment-detail.component';
import { SchoolYearEnrollmentPopupComponent } from './school-year-enrollment-dialog.component';
import { SchoolYearEnrollmentDeletePopupComponent } from './school-year-enrollment-delete-dialog.component';

@Injectable()
export class SchoolYearEnrollmentResolvePagingParams implements Resolve<any> {

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

export const schoolYearEnrollmentRoute: Routes = [
    {
        path: 'school-year-enrollment',
        component: SchoolYearEnrollmentComponent,
        resolve: {
            'pagingParams': SchoolYearEnrollmentResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.schoolYearEnrollment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'school-year-enrollment/:id',
        component: SchoolYearEnrollmentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.schoolYearEnrollment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const schoolYearEnrollmentPopupRoute: Routes = [
    {
        path: 'school-year-enrollment-new',
        component: SchoolYearEnrollmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.schoolYearEnrollment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'school-year-enrollment/:id/edit',
        component: SchoolYearEnrollmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.schoolYearEnrollment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'school-year-enrollment/:id/delete',
        component: SchoolYearEnrollmentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.schoolYearEnrollment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
