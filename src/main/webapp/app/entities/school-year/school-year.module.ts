import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ItehProjectSharedModule } from '../../shared';
import {
    SchoolYearService,
    SchoolYearPopupService,
    SchoolYearComponent,
    SchoolYearDetailComponent,
    SchoolYearDialogComponent,
    SchoolYearPopupComponent,
    SchoolYearDeletePopupComponent,
    SchoolYearDeleteDialogComponent,
    schoolYearRoute,
    schoolYearPopupRoute,
} from './';

const ENTITY_STATES = [
    ...schoolYearRoute,
    ...schoolYearPopupRoute,
];

@NgModule({
    imports: [
        ItehProjectSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SchoolYearComponent,
        SchoolYearDetailComponent,
        SchoolYearDialogComponent,
        SchoolYearDeleteDialogComponent,
        SchoolYearPopupComponent,
        SchoolYearDeletePopupComponent,
    ],
    entryComponents: [
        SchoolYearComponent,
        SchoolYearDialogComponent,
        SchoolYearPopupComponent,
        SchoolYearDeleteDialogComponent,
        SchoolYearDeletePopupComponent,
    ],
    providers: [
        SchoolYearService,
        SchoolYearPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectSchoolYearModule {}
