import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DatePipe } from '@angular/common';

import {
    ItehProjectSharedLibsModule,
    ItehProjectSharedCommonModule,
    CSRFService,
    AuthServerProvider,
    AccountService,
    UserService,
    StateStorageService,
    LoginService,
    LoginModalService,
    LoginComponent,
    Principal,
    JhiTrackerService,
    HasAnyAuthorityDirective,
    JhiSocialComponent,
    SocialService,
    ResolvePagingParams,
    FieldInputComponent,
    CheckboxInputComponent,
    DeleteDialogComponent,
    EntityEditDialogComponent
} from './';

@NgModule({
    imports: [
        ItehProjectSharedLibsModule,
        ItehProjectSharedCommonModule
    ],
    declarations: [
        JhiSocialComponent,
        LoginComponent,
        FieldInputComponent,
        CheckboxInputComponent,
        DeleteDialogComponent,
        EntityEditDialogComponent,
        HasAnyAuthorityDirective
    ],
    providers: [
        LoginService,
        LoginModalService,
        AccountService,
        ResolvePagingParams,
        StateStorageService,
        Principal,
        CSRFService,
        JhiTrackerService,
        AuthServerProvider,
        SocialService,
        UserService,
        DatePipe
    ],
    entryComponents: [LoginComponent],
    exports: [
        ItehProjectSharedCommonModule,
        JhiSocialComponent,
        LoginComponent,
        FieldInputComponent,
        CheckboxInputComponent,
        DeleteDialogComponent,
        EntityEditDialogComponent,
        HasAnyAuthorityDirective,
        DatePipe
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectSharedModule {}
