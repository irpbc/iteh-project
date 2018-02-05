import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ItehProjectSharedModule } from '../../shared';
import { ItehProjectAdminModule } from '../../admin/admin.module';
import {
    FacebookPostProposalService,
    FacebookPostProposalPopupService,
    FacebookPostProposalComponent,
    FacebookPostProposalDetailComponent,
    FacebookPostProposalDialogComponent,
    FacebookPostProposalPopupComponent,
    FacebookPostProposalDeleteDialogComponent,
    facebookPostProposalRoute,
    facebookPostProposalPopupRoute,
} from './';

const ENTITY_STATES = [
    ...facebookPostProposalRoute,
    ...facebookPostProposalPopupRoute,
];

@NgModule({
    imports: [
        ItehProjectSharedModule,
        ItehProjectAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        FacebookPostProposalComponent,
        FacebookPostProposalDetailComponent,
        FacebookPostProposalDialogComponent,
        FacebookPostProposalDeleteDialogComponent,
        FacebookPostProposalPopupComponent,
    ],
    entryComponents: [
        FacebookPostProposalComponent,
        FacebookPostProposalDialogComponent,
        FacebookPostProposalPopupComponent,
        FacebookPostProposalDeleteDialogComponent,
    ],
    providers: [
        FacebookPostProposalService,
        FacebookPostProposalPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectFacebookPostProposalModule {
}
