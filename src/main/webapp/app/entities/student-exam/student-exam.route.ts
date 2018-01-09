import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { StudentExamComponent } from './student-exam.component';
import { StudentExamDetailComponent } from './student-exam-detail.component';
import { StudentExamPopupComponent } from './student-exam-dialog.component';
import { StudentExamDeletePopupComponent } from './student-exam-delete-dialog.component';

@Injectable()
export class StudentExamResolvePagingParams implements Resolve<any> {

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

export const studentExamRoute: Routes = [
    {
        path: 'student-exam',
        component: StudentExamComponent,
        resolve: {
            'pagingParams': StudentExamResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.studentExam.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'student-exam/:id',
        component: StudentExamDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.studentExam.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const studentExamPopupRoute: Routes = [
    {
        path: 'student-exam-new',
        component: StudentExamPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.studentExam.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'student-exam/:id/edit',
        component: StudentExamPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.studentExam.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'student-exam/:id/delete',
        component: StudentExamDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.studentExam.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
