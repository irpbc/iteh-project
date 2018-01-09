import { BaseEntity } from './../../shared';

export class SchoolYearEnrollment implements BaseEntity {
    constructor(
        public id?: number,
        public averageGrade?: number,
        public espbPointsDeclared?: number,
        public espbPointsAttained?: number,
        public studentId?: number,
        public yearId?: number,
    ) {
    }
}
