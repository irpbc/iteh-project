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
    JhiLoginModalComponent,
    Principal,
    JhiTrackerService,
    HasAnyAuthorityDirective,
    JhiSocialComponent,
    SocialService,
    ResolvePagingParams,
    FieldInputComponent,
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
        JhiLoginModalComponent,
        FieldInputComponent,
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
    entryComponents: [JhiLoginModalComponent],
    exports: [
        ItehProjectSharedCommonModule,
        JhiSocialComponent,
        JhiLoginModalComponent,
        FieldInputComponent,
        DeleteDialogComponent,
        EntityEditDialogComponent,
        HasAnyAuthorityDirective,
        DatePipe
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectSharedModule {}
