import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ItehProjectSharedModule } from '../../shared';
import {
    SemesterComponent,
    SemesterDeleteDialogComponent,
    SemesterDetailComponent,
    SemesterDialogComponent,
    semesterPopupRoute,
    semesterRoute,
    SemesterService
} from './';

const ENTITY_STATES = [
    ...semesterRoute,
    ...semesterPopupRoute,
];

@NgModule({
    imports: [
        ItehProjectSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SemesterComponent,
        SemesterDetailComponent,
        SemesterDialogComponent,
        SemesterDeleteDialogComponent,
    ],
    entryComponents: [
        SemesterComponent,
        SemesterDialogComponent,
        SemesterDeleteDialogComponent,
    ],
    providers: [
        SemesterService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectSemesterModule {}
