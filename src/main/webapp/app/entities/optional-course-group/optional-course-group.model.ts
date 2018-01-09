import { BaseEntity } from './../../shared';

export class OptionalCourseGroup implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public semesterId?: number,
    ) {
    }
}
