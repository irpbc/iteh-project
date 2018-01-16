import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ItehProjectSharedModule } from '../../shared';
import {
    ExamService,
    ExamPopupService,
    ExamComponent,
    ExamDetailComponent,
    ExamDialogComponent,
    ExamPopupComponent,
    ExamDeletePopupComponent,
    ExamDeleteDialogComponent,
    examRoute,
    examPopupRoute,
} from './';

const ENTITY_STATES = [
    ...examRoute,
    ...examPopupRoute,
];

@NgModule({
    imports: [
        ItehProjectSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ExamComponent,
        ExamDetailComponent,
        ExamDialogComponent,
        ExamDeleteDialogComponent,
        ExamPopupComponent,
        ExamDeletePopupComponent,
    ],
    entryComponents: [
        ExamComponent,
        ExamDialogComponent,
        ExamPopupComponent,
        ExamDeleteDialogComponent,
        ExamDeletePopupComponent,
    ],
    providers: [
        ExamService,
        ExamPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectExamModule {}
