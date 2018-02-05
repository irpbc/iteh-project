import { Routes } from '@angular/router';

import { ResolvePagingParams, UserRouteAccessService } from '../../shared';
import { FacebookPostProposalComponent } from './facebook-post-proposal.component';
import { FacebookPostProposalDetailComponent } from './facebook-post-proposal-detail.component';
import { FacebookPostProposalPopupComponent } from './facebook-post-proposal-dialog.component';
import { FacebookPostProposalDeleteDialogComponent } from './facebook-post-proposal-delete-dialog.component';

export const facebookPostProposalRoute: Routes = [
    {
        path: 'facebook-post-proposal',
        component: FacebookPostProposalComponent,
        resolve: {
            'pagingParams': ResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'app.facebookPostProposal.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'facebook-post-proposal/:id',
        component: FacebookPostProposalDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'app.facebookPostProposal.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const facebookPostProposalPopupRoute: Routes = [
    {
        path: 'facebook-post-proposal-new',
        component: FacebookPostProposalPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'app.facebookPostProposal.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'facebook-post-proposal/:id/edit',
        component: FacebookPostProposalPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'app.facebookPostProposal.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'facebook-post-proposal/:id/delete',
        component: FacebookPostProposalDeleteDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'app.facebookPostProposal.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
