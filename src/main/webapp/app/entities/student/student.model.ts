import { BaseEntity } from './../../shared';

export class Student implements BaseEntity {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
    ) {
    }
}
