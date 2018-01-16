import { Routes } from '@angular/router';

import { ResolvePagingParams, UserRouteAccessService } from '../../shared';
import { StudentExamComponent } from './student-exam.component';
import { StudentExamDetailComponent } from './student-exam-detail.component';
import { StudentExamPopupComponent } from './student-exam-dialog.component';
import { StudentExamDeletePopupComponent } from './student-exam-delete-dialog.component';

export const studentExamRoute: Routes = [
    {
        path: 'student-exam',
        component: StudentExamComponent,
        resolve: {
            'pagingParams': ResolvePagingParams
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
