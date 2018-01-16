import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ItehProjectSharedModule } from '../../shared';
import { ItehProjectAdminModule } from '../../admin/admin.module';
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
} from './';

const ENTITY_STATES = [
    ...schoolYearEnrollmentRoute,
    ...schoolYearEnrollmentPopupRoute,
];

@NgModule({
    imports: [
        ItehProjectSharedModule,
        ItehProjectAdminModule,
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
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectSchoolYearEnrollmentModule {}
