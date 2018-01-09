import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ItehProjectSharedModule } from '../../shared';
import { CheckboxModule } from 'primeng/primeng';
import { SpinnerModule } from 'primeng/primeng';
import {
    StudentExamService,
    StudentExamPopupService,
    StudentExamComponent,
    StudentExamDetailComponent,
    StudentExamDialogComponent,
    StudentExamPopupComponent,
    StudentExamDeletePopupComponent,
    StudentExamDeleteDialogComponent,
    studentExamRoute,
    studentExamPopupRoute,
    StudentExamResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...studentExamRoute,
    ...studentExamPopupRoute,
];

@NgModule({
    imports: [
        ItehProjectSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        StudentExamComponent,
        StudentExamDetailComponent,
        StudentExamDialogComponent,
        StudentExamDeleteDialogComponent,
        StudentExamPopupComponent,
        StudentExamDeletePopupComponent,
    ],
    entryComponents: [
        StudentExamComponent,
        StudentExamDialogComponent,
        StudentExamPopupComponent,
        StudentExamDeleteDialogComponent,
        StudentExamDeletePopupComponent,
    ],
    providers: [
        StudentExamService,
        StudentExamPopupService,
        StudentExamResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectStudentExamModule {}
