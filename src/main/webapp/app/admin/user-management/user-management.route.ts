import { Injectable } from '@angular/core';
import { CanActivate, Routes } from '@angular/router';

import { UserMgmtComponent } from './user-management.component';
import { UserMgmtDetailComponent } from './user-management-detail.component';
import { UserDialogComponent } from './user-management-dialog.component';
import { UserDeleteDialogComponent } from './user-management-delete-dialog.component';

import { Principal, ResolvePagingParams } from '../../shared';

@Injectable()
export class UserResolve implements CanActivate {

    constructor(private principal: Principal) { }

    canActivate() {
        return this.principal.identity().then((account) => this.principal.hasAnyAuthority(['ROLE_ADMIN']));
    }
}

export const userMgmtRoute: Routes = [
    {
        path: 'user-management',
        component: UserMgmtComponent,
        resolve: {
            'pagingParams': ResolvePagingParams
        },
        data: {
            pageTitle: 'app.user.home.title'
        }
    },
    {
        path: 'user-management/:login',
        component: UserMgmtDetailComponent,
        data: {
            pageTitle: 'app.user.home.title'
        }
    }
];

export const userDialogRoute: Routes = [
    {
        path: 'user-management-new',
        component: UserDialogComponent,
        outlet: 'popup'
    },
    {
        path: 'user-management/:login/edit',
        component: UserDialogComponent,
        outlet: 'popup'
    },
    {
        path: 'user-management/:login/delete',
        component: UserDeleteDialogComponent,
        outlet: 'popup'
    }
];
