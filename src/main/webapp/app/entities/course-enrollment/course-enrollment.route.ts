import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { CourseEnrollmentComponent } from './course-enrollment.component';
import { CourseEnrollmentDetailComponent } from './course-enrollment-detail.component';
import { CourseEnrollmentPopupComponent } from './course-enrollment-dialog.component';
import { CourseEnrollmentDeletePopupComponent } from './course-enrollment-delete-dialog.component';

@Injectable()
export class CourseEnrollmentResolvePagingParams implements Resolve<any> {

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

export const courseEnrollmentRoute: Routes = [
    {
        path: 'course-enrollment',
        component: CourseEnrollmentComponent,
        resolve: {
            'pagingParams': CourseEnrollmentResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.courseEnrollment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'course-enrollment/:id',
        component: CourseEnrollmentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.courseEnrollment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const courseEnrollmentPopupRoute: Routes = [
    {
        path: 'course-enrollment-new',
        component: CourseEnrollmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.courseEnrollment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'course-enrollment/:id/edit',
        component: CourseEnrollmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.courseEnrollment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'course-enrollment/:id/delete',
        component: CourseEnrollmentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.courseEnrollment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
