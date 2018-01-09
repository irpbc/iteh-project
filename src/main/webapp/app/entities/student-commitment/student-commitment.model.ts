import { BaseEntity } from './../../shared';

export class StudentCommitment implements BaseEntity {
    constructor(
        public id?: number,
        public points?: number,
        public enrollmentId?: number,
        public commitmentId?: number,
        public evaluatedById?: number,
    ) {
    }
}
