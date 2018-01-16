import { Routes } from '@angular/router';

import { ResolvePagingParams, UserRouteAccessService } from '../../shared';
import { CourseEnrollmentComponent } from './course-enrollment.component';
import { CourseEnrollmentDetailComponent } from './course-enrollment-detail.component';
import { CourseEnrollmentPopupComponent } from './course-enrollment-dialog.component';
import { CourseEnrollmentDeletePopupComponent } from './course-enrollment-delete-dialog.component';

export const courseEnrollmentRoute: Routes = [
    {
        path: 'course-enrollment',
        component: CourseEnrollmentComponent,
        resolve: {
            'pagingParams': ResolvePagingParams
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
