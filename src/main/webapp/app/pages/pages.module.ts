import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ItehProjectSharedModule } from '../shared';

import {
    PassedCoursesComponent,
    DueCoursesComponent,
    pageRoutes
} from './';

@NgModule({
    imports: [
        ItehProjectSharedModule,
        RouterModule.forChild(pageRoutes)
    ],
    declarations: [
        PassedCoursesComponent,
        DueCoursesComponent
    ],
    entryComponents: [
        PassedCoursesComponent,
        DueCoursesComponent
    ]
})
export class PagesModule {
}
