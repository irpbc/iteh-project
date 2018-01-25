import { Routes } from '@angular/router';

import { ResolvePagingParams, UserRouteAccessService } from '../../shared';
import { ExamComponent } from './exam.component';
import { ExamDetailComponent } from './exam-detail.component';
import { ExamPopupComponent } from './exam-dialog.component';
import { ExamDeletePopupComponent } from './exam-delete-dialog.component';

export const examRoute: Routes = [
    {
        path: 'exam',
        component: ExamComponent,
        resolve: {
            'pagingParams': ResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'app.exam.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'exam/:id',
        component: ExamDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'app.exam.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const examPopupRoute: Routes = [
    {
        path: 'exam-new',
        component: ExamPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'app.exam.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'exam/:id/edit',
        component: ExamPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'app.exam.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'exam/:id/delete',
        component: ExamDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'app.exam.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
