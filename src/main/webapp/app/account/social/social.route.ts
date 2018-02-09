import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SocialAuthComponent } from './social-auth.component';
import { UnknownFacebookProfileComponent } from './unknown-facebook-profile.component';

export const socialAuthRoute: Routes = [
    {
        path: 'social-auth',
        component: SocialAuthComponent,
        data: {
            authorities: [],
            pageTitle: 'social.register.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cannot-connect-facebook-profile',
        component: UnknownFacebookProfileComponent,
        canActivate: [UserRouteAccessService]
    }
];
