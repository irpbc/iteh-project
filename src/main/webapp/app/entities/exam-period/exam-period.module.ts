import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ItehProjectSharedModule } from '../../shared';
import {
    ExamPeriodService,
    ExamPeriodPopupService,
    ExamPeriodComponent,
    ExamPeriodDetailComponent,
    ExamPeriodDialogComponent,
    ExamPeriodPopupComponent,
    ExamPeriodDeletePopupComponent,
    ExamPeriodDeleteDialogComponent,
    examPeriodRoute,
    examPeriodPopupRoute,
    ExamPeriodResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...examPeriodRoute,
    ...examPeriodPopupRoute,
];

@NgModule({
    imports: [
        ItehProjectSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ExamPeriodComponent,
        ExamPeriodDetailComponent,
        ExamPeriodDialogComponent,
        ExamPeriodDeleteDialogComponent,
        ExamPeriodPopupComponent,
        ExamPeriodDeletePopupComponent,
    ],
    entryComponents: [
        ExamPeriodComponent,
        ExamPeriodDialogComponent,
        ExamPeriodPopupComponent,
        ExamPeriodDeleteDialogComponent,
        ExamPeriodDeletePopupComponent,
    ],
    providers: [
        ExamPeriodService,
        ExamPeriodPopupService,
        ExamPeriodResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectExamPeriodModule {}
