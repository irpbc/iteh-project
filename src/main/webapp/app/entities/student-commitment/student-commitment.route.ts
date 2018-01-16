import { Routes } from '@angular/router';

import { ResolvePagingParams, UserRouteAccessService } from '../../shared';
import { StudentCommitmentComponent } from './student-commitment.component';
import { StudentCommitmentDetailComponent } from './student-commitment-detail.component';
import { StudentCommitmentPopupComponent } from './student-commitment-dialog.component';
import { StudentCommitmentDeletePopupComponent } from './student-commitment-delete-dialog.component';

export const studentCommitmentRoute: Routes = [
    {
        path: 'student-commitment',
        component: StudentCommitmentComponent,
        resolve: {
            'pagingParams': ResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.studentCommitment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'student-commitment/:id',
        component: StudentCommitmentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.studentCommitment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const studentCommitmentPopupRoute: Routes = [
    {
        path: 'student-commitment-new',
        component: StudentCommitmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.studentCommitment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'student-commitment/:id/edit',
        component: StudentCommitmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.studentCommitment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'student-commitment/:id/delete',
        component: StudentCommitmentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.studentCommitment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
