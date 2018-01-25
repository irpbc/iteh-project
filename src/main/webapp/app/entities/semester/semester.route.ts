import { Routes } from '@angular/router';

import { ResolvePagingParams, UserRouteAccessService } from '../../shared';
import { SemesterComponent } from './semester.component';
import { SemesterDetailComponent } from './semester-detail.component';
import { SemesterDialogComponent } from './semester-dialog.component';
import { SemesterDeleteDialogComponent } from './semester-delete-dialog.component';

export const semesterRoute: Routes = [
    {
        path: 'semester',
        component: SemesterComponent,
        resolve: {
            'pagingParams': ResolvePagingParams
        },
        data: {
            authorities: ['ROLE_SERVICE'],
            pageTitle: 'app.semester.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'semester/:id',
        component: SemesterDetailComponent,
        data: {
            authorities: ['ROLE_SERVICE'],
            pageTitle: 'app.semester.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const semesterPopupRoute: Routes = [
    {
        path: 'semester-new',
        component: SemesterDialogComponent,
        data: {
            authorities: ['ROLE_SERVICE'],
            pageTitle: 'app.semester.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'semester/:id/edit',
        component: SemesterDialogComponent,
        data: {
            authorities: ['ROLE_SERVICE'],
            pageTitle: 'app.semester.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'semester/:id/delete',
        component: SemesterDeleteDialogComponent,
        data: {
            authorities: ['ROLE_SERVICE'],
            pageTitle: 'app.semester.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
