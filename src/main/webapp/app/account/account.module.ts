import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ItehProjectSharedModule } from '../shared';

import {
    ActivateService,
    PasswordService,
    PasswordResetInitService,
    PasswordResetFinishService,
    PasswordStrengthBarComponent,
    ActivateComponent,
    PasswordComponent,
    PasswordResetInitComponent,
    PasswordResetFinishComponent,
    SettingsComponent,
    SocialAuthComponent,
    UnknownFacebookProfileComponent,
    accountState
} from './';

@NgModule({
    imports: [
        ItehProjectSharedModule,
        RouterModule.forChild(accountState)
    ],
    declarations: [
        SocialAuthComponent,
        UnknownFacebookProfileComponent,
        ActivateComponent,
        PasswordComponent,
        PasswordStrengthBarComponent,
        PasswordResetInitComponent,
        PasswordResetFinishComponent,
        SettingsComponent
    ],
    entryComponents: [
        UnknownFacebookProfileComponent
    ],
    providers: [
        ActivateService,
        PasswordService,
        PasswordResetInitService,
        PasswordResetFinishService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectAccountModule {
}
