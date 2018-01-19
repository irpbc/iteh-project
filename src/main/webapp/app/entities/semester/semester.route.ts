import { Routes } from '@angular/router';

import { ResolvePagingParams, UserRouteAccessService } from '../../shared';
import { SemesterComponent } from './semester.component';
import { SemesterDetailComponent } from './semester-detail.component';
import { SemesterPopupComponent } from './semester-dialog.component';
import { SemesterDeletePopupComponent } from './semester-delete-dialog.component';

export const semesterRoute: Routes = [
    {
        path: 'semester',
        component: SemesterComponent,
        resolve: {
            'pagingParams': ResolvePagingParams
        },
        data: {
            authorities: ['ROLE_SERVICE'],
            pageTitle: 'itehProjectApp.semester.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'semester/:id',
        component: SemesterDetailComponent,
        data: {
            authorities: ['ROLE_SERVICE'],
            pageTitle: 'itehProjectApp.semester.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const semesterPopupRoute: Routes = [
    {
        path: 'semester-new',
        component: SemesterPopupComponent,
        data: {
            authorities: ['ROLE_SERVICE'],
            pageTitle: 'itehProjectApp.semester.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'semester/:id/edit',
        component: SemesterPopupComponent,
        data: {
            authorities: ['ROLE_SERVICE'],
            pageTitle: 'itehProjectApp.semester.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'semester/:id/delete',
        component: SemesterDeletePopupComponent,
        data: {
            authorities: ['ROLE_SERVICE'],
            pageTitle: 'itehProjectApp.semester.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
