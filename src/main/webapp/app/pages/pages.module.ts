import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ItehProjectSharedModule } from '../shared';

import {
    PassedCoursesComponent,
    DueCoursesComponent,
    ExamApplicationComponent,
    pageRoutes
} from './';

@NgModule({
    imports: [
        ItehProjectSharedModule,
        RouterModule.forChild(pageRoutes)
    ],
    declarations: [
        PassedCoursesComponent,
        DueCoursesComponent,
        ExamApplicationComponent
    ],
    entryComponents: [
        PassedCoursesComponent,
        DueCoursesComponent,
        ExamApplicationComponent
    ]
})
export class PagesModule {
}
