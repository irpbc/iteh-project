import { Routes } from '@angular/router';

import { ResolvePagingParams, UserRouteAccessService } from '../../shared';
import { CommitmentComponent } from './commitment.component';
import { CommitmentDetailComponent } from './commitment-detail.component';
import { CommitmentPopupComponent } from './commitment-dialog.component';
import { CommitmentDeletePopupComponent } from './commitment-delete-dialog.component';

export const commitmentRoute: Routes = [
    {
        path: 'commitment',
        component: CommitmentComponent,
        resolve: {
            'pagingParams': ResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.commitment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'commitment/:id',
        component: CommitmentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.commitment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commitmentPopupRoute: Routes = [
    {
        path: 'commitment-new',
        component: CommitmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.commitment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'commitment/:id/edit',
        component: CommitmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.commitment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'commitment/:id/delete',
        component: CommitmentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.commitment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
