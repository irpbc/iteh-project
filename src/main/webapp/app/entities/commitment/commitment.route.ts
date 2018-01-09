import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { CommitmentComponent } from './commitment.component';
import { CommitmentDetailComponent } from './commitment-detail.component';
import { CommitmentPopupComponent } from './commitment-dialog.component';
import { CommitmentDeletePopupComponent } from './commitment-delete-dialog.component';

@Injectable()
export class CommitmentResolvePagingParams implements Resolve<any> {

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

export const commitmentRoute: Routes = [
    {
        path: 'commitment',
        component: CommitmentComponent,
        resolve: {
            'pagingParams': CommitmentResolvePagingParams
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
