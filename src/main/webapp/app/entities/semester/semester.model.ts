import { BaseEntity } from './../../shared';

export class Semester implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public yearId?: number,
        public yearName?: string,
    ) {
    }
}
