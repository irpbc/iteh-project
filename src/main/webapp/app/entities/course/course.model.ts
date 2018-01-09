import { BaseEntity } from './../../shared';

export class Course implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public espbPoints?: number,
        public yearOfStudies?: number,
        public optional?: boolean,
        public semesterId?: number,
        public optionalGroupId?: number,
        public lecturers?: BaseEntity[],
    ) {
        this.optional = false;
    }
}
