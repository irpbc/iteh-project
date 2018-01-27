import { Routes } from '@angular/router';
import { ResolvePagingParams } from '../shared';
import { PassedCoursesComponent } from './passed-courses/passed-courses.component';
import { DueCoursesComponent } from './due-courses/due-courses.component';

export const pageRoutes: Routes = [
    {
        path: 'passed-courses',
        component: PassedCoursesComponent,
        data: {
            pageTitle: 'app.page.passedCourses.title'
        },
        resolve: {
            pagingParams: ResolvePagingParams
        }
    },
    {
        path: 'due-courses',
        component: DueCoursesComponent,
        data: {
            pageTitle: 'app.page.dueCourses.title'
        },
        resolve: {
            pagingParams: ResolvePagingParams
        }
    }
];
