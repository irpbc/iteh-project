import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ItehProjectSharedModule } from '../../shared';

import {
    StudentComponent,
    StudentDeleteDialogComponent,
    StudentDetailComponent,
    StudentDialogComponent,
    StudentDialogPopupComponent,
    StudentPopupService,
    studentDialogRoute,
    studentRoute
} from './';

const ROUTES = [
    ...studentRoute,
    ...studentDialogRoute,
];

@NgModule({
    imports: [
        ItehProjectSharedModule,
        RouterModule.forChild(ROUTES)
    ],
    declarations: [
        StudentComponent,
        StudentDetailComponent,
        StudentDialogComponent,
        StudentDialogPopupComponent,
        StudentDeleteDialogComponent,
    ],
    entryComponents: [
        StudentComponent,
        StudentDialogComponent,
        StudentDialogPopupComponent,
        StudentDeleteDialogComponent,
    ],
    providers: [
        StudentPopupService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectStudentModule {}
