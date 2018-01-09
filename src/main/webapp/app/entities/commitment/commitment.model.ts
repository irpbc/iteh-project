import { BaseEntity } from './../../shared';

export class Commitment implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public maxPoints?: number,
        public courseId?: number,
    ) {
    }
}
