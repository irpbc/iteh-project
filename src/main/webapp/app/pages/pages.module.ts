import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ItehProjectSharedModule } from '../shared';
import { StudentCoursesComponent } from './student-courses/student-courses.component';
import { pageRoutes } from './pages.route';

@NgModule({
    imports: [
        ItehProjectSharedModule,
        RouterModule.forChild(pageRoutes)
    ],
    declarations: [
        StudentCoursesComponent
    ],
    entryComponents: [
        StudentCoursesComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PagesModule {
}
