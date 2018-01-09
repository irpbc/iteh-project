import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ExamPeriodComponent } from './exam-period.component';
import { ExamPeriodDetailComponent } from './exam-period-detail.component';
import { ExamPeriodPopupComponent } from './exam-period-dialog.component';
import { ExamPeriodDeletePopupComponent } from './exam-period-delete-dialog.component';

@Injectable()
export class ExamPeriodResolvePagingParams implements Resolve<any> {

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

export const examPeriodRoute: Routes = [
    {
        path: 'exam-period',
        component: ExamPeriodComponent,
        resolve: {
            'pagingParams': ExamPeriodResolvePagingParams
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
