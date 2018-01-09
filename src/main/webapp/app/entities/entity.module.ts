import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ItehProjectCourseModule } from './course/course.module';
import { ItehProjectSchoolYearModule } from './school-year/school-year.module';
import { ItehProjectStudentModule } from './student/student.module';
import { ItehProjectCourseEnrollmentModule } from './course-enrollment/course-enrollment.module';
import { ItehProjectSchoolYearEnrollmentModule } from './school-year-enrollment/school-year-enrollment.module';
import { ItehProjectCommitmentModule } from './commitment/commitment.module';
import { ItehProjectStudentCommitmentModule } from './student-commitment/student-commitment.module';
import { ItehProjectLecturerModule } from './lecturer/lecturer.module';
import { ItehProjectExamPeriodModule } from './exam-period/exam-period.module';
import { ItehProjectExamModule } from './exam/exam.module';
import { ItehProjectStudentExamModule } from './student-exam/student-exam.module';
import { ItehProjectSemesterModule } from './semester/semester.module';
import { ItehProjectOptionalCourseGroupModule } from './optional-course-group/optional-course-group.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ItehProjectCourseModule,
        ItehProjectSchoolYearModule,
        ItehProjectStudentModule,
        ItehProjectCourseEnrollmentModule,
        ItehProjectSchoolYearEnrollmentModule,
        ItehProjectCommitmentModule,
        ItehProjectStudentCommitmentModule,
        ItehProjectLecturerModule,
        ItehProjectExamPeriodModule,
        ItehProjectExamModule,
        ItehProjectStudentExamModule,
        ItehProjectSemesterModule,
        ItehProjectOptionalCourseGroupModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectEntityModule {}
