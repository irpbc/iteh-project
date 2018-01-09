import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ItehProjectSharedModule } from '../../shared';
import { InputTextModule } from 'primeng/primeng';
import {
    LecturerService,
    LecturerPopupService,
    LecturerComponent,
    LecturerDetailComponent,
    LecturerDialogComponent,
    LecturerPopupComponent,
    LecturerDeletePopupComponent,
    LecturerDeleteDialogComponent,
    lecturerRoute,
    lecturerPopupRoute,
    LecturerResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...lecturerRoute,
    ...lecturerPopupRoute,
];

@NgModule({
    imports: [
        ItehProjectSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        LecturerComponent,
        LecturerDetailComponent,
        LecturerDialogComponent,
        LecturerDeleteDialogComponent,
        LecturerPopupComponent,
        LecturerDeletePopupComponent,
    ],
    entryComponents: [
        LecturerComponent,
        LecturerDialogComponent,
        LecturerPopupComponent,
        LecturerDeleteDialogComponent,
        LecturerDeletePopupComponent,
    ],
    providers: [
        LecturerService,
        LecturerPopupService,
        LecturerResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectLecturerModule {}
