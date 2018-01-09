import { BaseEntity } from './../../shared';

export class ExamPeriod implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public startDate?: any,
        public endDate?: any,
        public yearId?: number,
    ) {
    }
}
