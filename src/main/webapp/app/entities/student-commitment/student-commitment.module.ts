import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ItehProjectSharedModule } from '../../shared';
import { SpinnerModule } from 'primeng/primeng';
import {
    StudentCommitmentService,
    StudentCommitmentPopupService,
    StudentCommitmentComponent,
    StudentCommitmentDetailComponent,
    StudentCommitmentDialogComponent,
    StudentCommitmentPopupComponent,
    StudentCommitmentDeletePopupComponent,
    StudentCommitmentDeleteDialogComponent,
    studentCommitmentRoute,
    studentCommitmentPopupRoute,
    StudentCommitmentResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...studentCommitmentRoute,
    ...studentCommitmentPopupRoute,
];

@NgModule({
    imports: [
        ItehProjectSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        StudentCommitmentComponent,
        StudentCommitmentDetailComponent,
        StudentCommitmentDialogComponent,
        StudentCommitmentDeleteDialogComponent,
        StudentCommitmentPopupComponent,
        StudentCommitmentDeletePopupComponent,
    ],
    entryComponents: [
        StudentCommitmentComponent,
        StudentCommitmentDialogComponent,
        StudentCommitmentPopupComponent,
        StudentCommitmentDeleteDialogComponent,
        StudentCommitmentDeletePopupComponent,
    ],
    providers: [
        StudentCommitmentService,
        StudentCommitmentPopupService,
        StudentCommitmentResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectStudentCommitmentModule {}
