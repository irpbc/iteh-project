import { BaseEntity } from './../../shared';

export class Lecturer implements BaseEntity {
    constructor(
        public id?: number,
        public login?: string,
        public email?: string,
        public firstName?: string,
        public lastName?: string,
        public courses?: BaseEntity[],
    ) {
    }
}
