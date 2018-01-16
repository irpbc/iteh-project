import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ItehProjectSharedModule } from '../../shared';
import {
    OptionalCourseGroupService,
    OptionalCourseGroupPopupService,
    OptionalCourseGroupComponent,
    OptionalCourseGroupDetailComponent,
    OptionalCourseGroupDialogComponent,
    OptionalCourseGroupPopupComponent,
    OptionalCourseGroupDeletePopupComponent,
    OptionalCourseGroupDeleteDialogComponent,
    optionalCourseGroupRoute,
    optionalCourseGroupPopupRoute,
} from './';

const ENTITY_STATES = [
    ...optionalCourseGroupRoute,
    ...optionalCourseGroupPopupRoute,
];

@NgModule({
    imports: [
        ItehProjectSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        OptionalCourseGroupComponent,
        OptionalCourseGroupDetailComponent,
        OptionalCourseGroupDialogComponent,
        OptionalCourseGroupDeleteDialogComponent,
        OptionalCourseGroupPopupComponent,
        OptionalCourseGroupDeletePopupComponent,
    ],
    entryComponents: [
        OptionalCourseGroupComponent,
        OptionalCourseGroupDialogComponent,
        OptionalCourseGroupPopupComponent,
        OptionalCourseGroupDeleteDialogComponent,
        OptionalCourseGroupDeletePopupComponent,
    ],
    providers: [
        OptionalCourseGroupService,
        OptionalCourseGroupPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectOptionalCourseGroupModule {}
