import { BaseEntity } from './../../shared';

export const enum FacebookPostKind {
    'EXAM_PASSED',
    ' EXAM_FAILED'
}

export class FacebookPostProposal implements BaseEntity {
    constructor(
        public id?: number,
        public kind?: FacebookPostKind,
        public data?: {
            course?: string;
            evaluatedBy?: string;
            period?: string;
            grade?: number;
        },
        public time?: any,
        public studentId?: number,
        public studentName?: string,
    ) {
    }
}
