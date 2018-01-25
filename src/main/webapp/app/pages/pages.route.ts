import { Routes } from '@angular/router';
import { StudentCoursesComponent } from './student-courses/student-courses.component';
import { ResolvePagingParams } from '../shared';

export const pageRoutes: Routes = [
    {
        path: 'student-courses',
        component: StudentCoursesComponent,
        data: {
            pageTitle: 'TODO'
        },
        resolve: {
            'pagingParams': ResolvePagingParams
        }
    }
];
