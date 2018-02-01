export enum FieldType {
    STRING = 'STRING',
    INTEGER = 'INTEGER',
    DECIMAL = 'DECIMAL',
    DATE = 'DATE',
    DATETIME = 'DATETIME',
    ENUM = 'ENUM',
    REL_TO_ONE = 'REL_TO_ONE',
    REL_TO_MANY = 'REL_TO_MANY',
}

export interface FieldDef {
    name: string;
    type: FieldType;
    required?: boolean;
    email?: boolean;
    min?: number;
    max?: number;
    minLength?: number;
    maxLength?: number;

    options?: any[];
    optionNameField?: string;
}
