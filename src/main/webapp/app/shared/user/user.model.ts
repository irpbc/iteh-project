export enum UserType {
    ST = 'ST',
    LC = 'LC',
    SR = 'SR',
    AD = 'AD',
    SY = 'SY'
}

export const CREATABLE_TYPES: UserType[] = [ UserType.AD, UserType.LC, UserType.SR, UserType.ST ];

export class User {
    public id?: number;
    public userType?: UserType;
    public login?: string;
    public firstName?: string;
    public lastName?: string;
    public fullName?: string;
    public email?: string;
    public activated?: Boolean;
    public langKey?: string;
    public authorities?: any[];
    public createdBy?: string;
    public createdDate?: Date;
    public lastModifiedBy?: string;
    public lastModifiedDate?: Date;
    public password?: string;

    constructor(id?: number,
                userType?: UserType,
                login?: string,
                firstName?: string,
                lastName?: string,
                email?: string,
                activated?: Boolean,
                langKey?: string,
                authorities?: any[],
                createdBy?: string,
                createdDate?: Date,
                lastModifiedBy?: string,
                lastModifiedDate?: Date,
                password?: string) {
        this.id               = id ? id : null;
        this.login            = login ? login : null;
        this.firstName        = firstName ? firstName : null;
        this.lastName         = lastName ? lastName : null;
        this.email            = email ? email : null;
        this.activated        = activated ? activated : false;
        this.langKey          = langKey ? langKey : null;
        this.authorities      = authorities ? authorities : null;
        this.createdBy        = createdBy ? createdBy : null;
        this.createdDate      = createdDate ? createdDate : null;
        this.lastModifiedBy   = lastModifiedBy ? lastModifiedBy : null;
        this.lastModifiedDate = lastModifiedDate ? lastModifiedDate : null;
        this.password         = password ? password : null;
    }
}
