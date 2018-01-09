import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ItehProjectSharedModule } from '../../shared';
import { InputTextModule } from 'primeng/primeng';
import { SpinnerModule } from 'primeng/primeng';
import {
    CommitmentService,
    CommitmentPopupService,
    CommitmentComponent,
    CommitmentDetailComponent,
    CommitmentDialogComponent,
    CommitmentPopupComponent,
    CommitmentDeletePopupComponent,
    CommitmentDeleteDialogComponent,
    commitmentRoute,
    commitmentPopupRoute,
    CommitmentResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...commitmentRoute,
    ...commitmentPopupRoute,
];

@NgModule({
    imports: [
        ItehProjectSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CommitmentComponent,
        CommitmentDetailComponent,
        CommitmentDialogComponent,
        CommitmentDeleteDialogComponent,
        CommitmentPopupComponent,
        CommitmentDeletePopupComponent,
    ],
    entryComponents: [
        CommitmentComponent,
        CommitmentDialogComponent,
        CommitmentPopupComponent,
        CommitmentDeleteDialogComponent,
        CommitmentDeletePopupComponent,
    ],
    providers: [
        CommitmentService,
        CommitmentPopupService,
        CommitmentResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectCommitmentModule {}
