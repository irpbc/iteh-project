import { Routes } from '@angular/router';

import { ResolvePagingParams, UserRouteAccessService } from '../../shared';
import { ExamPeriodComponent } from './exam-period.component';
import { ExamPeriodDetailComponent } from './exam-period-detail.component';
import { ExamPeriodPopupComponent } from './exam-period-dialog.component';
import { ExamPeriodDeletePopupComponent } from './exam-period-delete-dialog.component';

export const examPeriodRoute: Routes = [
    {
        path: 'exam-period',
        component: ExamPeriodComponent,
        resolve: {
            'pagingParams': ResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.examPeriod.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'exam-period/:id',
        component: ExamPeriodDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.examPeriod.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const examPeriodPopupRoute: Routes = [
    {
        path: 'exam-period-new',
        component: ExamPeriodPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.examPeriod.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'exam-period/:id/edit',
        component: ExamPeriodPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.examPeriod.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'exam-period/:id/delete',
        component: ExamPeriodDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.examPeriod.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
