import { Routes } from '@angular/router';

import { ResolvePagingParams, UserRouteAccessService } from '../../shared';
import { SchoolYearEnrollmentComponent } from './school-year-enrollment.component';
import { SchoolYearEnrollmentDetailComponent } from './school-year-enrollment-detail.component';
import { SchoolYearEnrollmentPopupComponent } from './school-year-enrollment-dialog.component';
import { SchoolYearEnrollmentDeletePopupComponent } from './school-year-enrollment-delete-dialog.component';

export const schoolYearEnrollmentRoute: Routes = [
    {
        path: 'school-year-enrollment',
        component: SchoolYearEnrollmentComponent,
        resolve: {
            'pagingParams': ResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'app.schoolYearEnrollment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'school-year-enrollment/:id',
        component: SchoolYearEnrollmentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'app.schoolYearEnrollment.home.title'
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
            pageTitle: 'app.schoolYearEnrollment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'school-year-enrollment/:id/edit',
        component: SchoolYearEnrollmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'app.schoolYearEnrollment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'school-year-enrollment/:id/delete',
        component: SchoolYearEnrollmentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'app.schoolYearEnrollment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
