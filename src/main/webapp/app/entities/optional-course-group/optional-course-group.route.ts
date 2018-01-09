import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { OptionalCourseGroupComponent } from './optional-course-group.component';
import { OptionalCourseGroupDetailComponent } from './optional-course-group-detail.component';
import { OptionalCourseGroupPopupComponent } from './optional-course-group-dialog.component';
import { OptionalCourseGroupDeletePopupComponent } from './optional-course-group-delete-dialog.component';

@Injectable()
export class OptionalCourseGroupResolvePagingParams implements Resolve<any> {

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

export const optionalCourseGroupRoute: Routes = [
    {
        path: 'optional-course-group',
        component: OptionalCourseGroupComponent,
        resolve: {
            'pagingParams': OptionalCourseGroupResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.optionalCourseGroup.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'optional-course-group/:id',
        component: OptionalCourseGroupDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.optionalCourseGroup.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const optionalCourseGroupPopupRoute: Routes = [
    {
        path: 'optional-course-group-new',
        component: OptionalCourseGroupPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.optionalCourseGroup.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'optional-course-group/:id/edit',
        component: OptionalCourseGroupPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.optionalCourseGroup.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'optional-course-group/:id/delete',
        component: OptionalCourseGroupDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'itehProjectApp.optionalCourseGroup.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
