import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ItehProjectSharedModule } from '../../shared';
import {
    SchoolYearService,
    SchoolYearComponent,
    SchoolYearDetailComponent,
    SchoolYearDialogComponent,
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
    ],
    entryComponents: [
        SchoolYearComponent,
        SchoolYearDialogComponent,
        SchoolYearDeleteDialogComponent,
    ],
    providers: [
        SchoolYearService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectSchoolYearModule {}
