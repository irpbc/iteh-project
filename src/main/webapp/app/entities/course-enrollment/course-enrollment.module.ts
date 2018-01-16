import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ItehProjectSharedModule } from '../../shared';
import {
    CourseEnrollmentService,
    CourseEnrollmentPopupService,
    CourseEnrollmentComponent,
    CourseEnrollmentDetailComponent,
    CourseEnrollmentDialogComponent,
    CourseEnrollmentPopupComponent,
    CourseEnrollmentDeletePopupComponent,
    CourseEnrollmentDeleteDialogComponent,
    courseEnrollmentRoute,
    courseEnrollmentPopupRoute,
} from './';

const ENTITY_STATES = [
    ...courseEnrollmentRoute,
    ...courseEnrollmentPopupRoute,
];

@NgModule({
    imports: [
        ItehProjectSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CourseEnrollmentComponent,
        CourseEnrollmentDetailComponent,
        CourseEnrollmentDialogComponent,
        CourseEnrollmentDeleteDialogComponent,
        CourseEnrollmentPopupComponent,
        CourseEnrollmentDeletePopupComponent,
    ],
    entryComponents: [
        CourseEnrollmentComponent,
        CourseEnrollmentDialogComponent,
        CourseEnrollmentPopupComponent,
        CourseEnrollmentDeleteDialogComponent,
        CourseEnrollmentDeletePopupComponent,
    ],
    providers: [
        CourseEnrollmentService,
        CourseEnrollmentPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectCourseEnrollmentModule {}
