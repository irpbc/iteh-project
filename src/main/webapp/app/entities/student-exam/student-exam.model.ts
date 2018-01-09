import { BaseEntity } from './../../shared';

export class StudentExam implements BaseEntity {
    constructor(
        public id?: number,
        public attended?: boolean,
        public grade?: number,
        public studentId?: number,
        public examId?: number,
        public evaluatedById?: number,
    ) {
        this.attended = false;
    }
}
