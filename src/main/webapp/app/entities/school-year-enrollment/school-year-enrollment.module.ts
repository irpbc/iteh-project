import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ItehProjectSharedModule } from '../../shared';
import { SpinnerModule } from 'primeng/primeng';
import {
    SchoolYearEnrollmentService,
    SchoolYearEnrollmentPopupService,
    SchoolYearEnrollmentComponent,
    SchoolYearEnrollmentDetailComponent,
    SchoolYearEnrollmentDialogComponent,
    SchoolYearEnrollmentPopupComponent,
    SchoolYearEnrollmentDeletePopupComponent,
    SchoolYearEnrollmentDeleteDialogComponent,
    schoolYearEnrollmentRoute,
    schoolYearEnrollmentPopupRoute,
    SchoolYearEnrollmentResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...schoolYearEnrollmentRoute,
    ...schoolYearEnrollmentPopupRoute,
];

@NgModule({
    imports: [
        ItehProjectSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SchoolYearEnrollmentComponent,
        SchoolYearEnrollmentDetailComponent,
        SchoolYearEnrollmentDialogComponent,
        SchoolYearEnrollmentDeleteDialogComponent,
        SchoolYearEnrollmentPopupComponent,
        SchoolYearEnrollmentDeletePopupComponent,
    ],
    entryComponents: [
        SchoolYearEnrollmentComponent,
        SchoolYearEnrollmentDialogComponent,
        SchoolYearEnrollmentPopupComponent,
        SchoolYearEnrollmentDeleteDialogComponent,
        SchoolYearEnrollmentDeletePopupComponent,
    ],
    providers: [
        SchoolYearEnrollmentService,
        SchoolYearEnrollmentPopupService,
        SchoolYearEnrollmentResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectSchoolYearEnrollmentModule {}
