import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ItehProjectSharedModule } from '../../shared';
import { InputTextModule } from 'primeng/primeng';
import {
    SemesterService,
    SemesterPopupService,
    SemesterComponent,
    SemesterDetailComponent,
    SemesterDialogComponent,
    SemesterPopupComponent,
    SemesterDeletePopupComponent,
    SemesterDeleteDialogComponent,
    semesterRoute,
    semesterPopupRoute,
    SemesterResolvePagingParams,
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
        SemesterPopupComponent,
        SemesterDeletePopupComponent,
    ],
    entryComponents: [
        SemesterComponent,
        SemesterDialogComponent,
        SemesterPopupComponent,
        SemesterDeleteDialogComponent,
        SemesterDeletePopupComponent,
    ],
    providers: [
        SemesterService,
        SemesterPopupService,
        SemesterResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectSemesterModule {}
