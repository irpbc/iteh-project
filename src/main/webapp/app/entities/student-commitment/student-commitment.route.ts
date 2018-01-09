import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { StudentCommitmentComponent } from './student-commitment.component';
import { StudentCommitmentDetailComponent } from './student-commitment-detail.component';
import { StudentCommitmentPopupComponent } from './student-commitment-dialog.component';
import { StudentCommitmentDeletePopupComponent } from './student-commitment-delete-dialog.component';

@Injectable()
export class StudentCommitmentResolvePagingParams implements Resolve<any> {

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

export const studentCommitmentRoute: Routes = [
    {
        path: 'student-commitment',
        component: StudentCommitmentComponent,
        resolve: {
            'pagingParams': StudentCommitmentResolvePagingParams
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
