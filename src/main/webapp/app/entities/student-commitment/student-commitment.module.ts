import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ItehProjectSharedModule } from '../../shared';
import { ItehProjectAdminModule } from '../../admin/admin.module';
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
} from './';

const ENTITY_STATES = [
    ...studentCommitmentRoute,
    ...studentCommitmentPopupRoute,
];

@NgModule({
    imports: [
        ItehProjectSharedModule,
        ItehProjectAdminModule,
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
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectStudentCommitmentModule {}
