import { BaseEntity } from '../../shared';

export class StudentExam implements BaseEntity {
    constructor(
        public id?: number,
        public attended?: boolean,
        public grade?: number,
        public studentId?: number,
        public studentFullName?: string,
        public examId?: number,
        public examName?: string,
        public evaluatedById?: number,
        public evaluatedByFullName?: string,
    ) {
        this.attended = false;
    }
}
