import { BaseEntity } from './../../shared';

export class CourseEnrollment implements BaseEntity {
    constructor(
        public id?: number,
        public totalPoints?: number,
        public grade?: number,
        public completed?: boolean,
        public yearEnrollmentId?: number,
        public courseId?: number,
    ) {
        this.completed = false;
    }
}
