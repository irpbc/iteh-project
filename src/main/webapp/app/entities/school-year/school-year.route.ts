import { Routes } from '@angular/router';

import { ResolvePagingParams, UserRouteAccessService } from '../../shared';
import { SchoolYearComponent } from './school-year.component';
import { SchoolYearDetailComponent } from './school-year-detail.component';
import { SchoolYearDialogComponent } from './school-year-dialog.component';
import { SchoolYearDeleteDialogComponent } from './school-year-delete-dialog.component';

export const schoolYearRoute: Routes = [
    {
        path: 'school-year',
        component: SchoolYearComponent,
        resolve: {
            'pagingParams': ResolvePagingParams
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
        component: SchoolYearDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.schoolYear.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'school-year/:id/edit',
        component: SchoolYearDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.schoolYear.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'school-year/:id/delete',
        component: SchoolYearDeleteDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.schoolYear.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
