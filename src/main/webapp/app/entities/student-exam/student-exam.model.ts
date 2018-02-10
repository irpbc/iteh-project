import { BaseEntity } from './../../shared';

export class StudentExam implements BaseEntity {
    constructor(
        public id?: number,
        public attended?: boolean,
        public grade?: number,
        public studentId?: number,
        public studentFullName?: number,
        public examId?: number,
        public examName?: number,
        public evaluatedById?: number,
        public evaluatedByFullName?: number,
    ) {
        this.attended = false;
    }
}
