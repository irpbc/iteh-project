import { BaseEntity } from './../../shared';

export class Exam implements BaseEntity {
    constructor(
        public id?: number,
        public time?: any,
        public periodId?: number,
        public periodName?: string,
        public courseId?: number,
        public courseName?: string,
    ) {
    }
}
