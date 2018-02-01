import { Routes } from '@angular/router';

import { ResolvePagingParams } from '../../shared';
import { StudentComponent } from './student.component';
import { StudentDetailComponent } from './student-detail.component';
import { StudentDialogPopupComponent } from './student-dialog.component';
import { StudentDeleteDialogComponent } from './student-delete-dialog.component';

export const studentRoute: Routes = [
    {
        path: 'student',
        component: StudentComponent,
        resolve: {
            'pagingParams': ResolvePagingParams
        },
        data: {
            authorities: ['ROLE_SERVICE', 'ROLE_LECTURER', 'ROLE_ADMIN'],
            pageTitle: 'app.student.home.title'
        }
    },
    {
        path: 'student/:id',
        component: StudentDetailComponent,
        data: {
            authorities: ['ROLE_SERVICE', 'ROLE_LECTURER', 'ROLE_ADMIN'],
            pageTitle: 'app.student.home.title'
        }
    }
];

export const studentDialogRoute: Routes = [
    {
        path: 'student-new',
        component: StudentDialogPopupComponent,
        outlet: 'popup',
        data: {
            authorities: ['ROLE_SERVICE', 'ROLE_ADMIN']
        }
    },
    {
        path: 'student/:id/edit',
        component: StudentDialogPopupComponent,
        outlet: 'popup',
        data: {
            authorities: ['ROLE_SERVICE', 'ROLE_ADMIN']
        }
    },
    {
        path: 'student/:id/delete',
        component: StudentDeleteDialogComponent,
        outlet: 'popup',
        data: {
            authorities: ['ROLE_SERVICE', 'ROLE_ADMIN']
        }
    }
];
