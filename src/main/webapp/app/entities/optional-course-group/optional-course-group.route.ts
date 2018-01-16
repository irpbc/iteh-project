import { Routes } from '@angular/router';

import { ResolvePagingParams, UserRouteAccessService } from '../../shared';
import { OptionalCourseGroupComponent } from './optional-course-group.component';
import { OptionalCourseGroupDetailComponent } from './optional-course-group-detail.component';
import { OptionalCourseGroupPopupComponent } from './optional-course-group-dialog.component';
import { OptionalCourseGroupDeletePopupComponent } from './optional-course-group-delete-dialog.component';

export const optionalCourseGroupRoute: Routes = [
    {
        path: 'optional-course-group',
        component: OptionalCourseGroupComponent,
        resolve: {
            'pagingParams': ResolvePagingParams
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
