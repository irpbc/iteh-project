export enum UserType {
    ST = 'ST',
    LC = 'LC',
    SR = 'SR',
    AD = 'AD',
    SY = 'SY'
}

export const CREATABLE_TYPES: UserType[] = [UserType.AD, UserType.LC, UserType.SR, UserType.ST];

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
    public code?: string;
    public authorities?: string[];
    public password?: string;
    public imageUrl?: string;

    constructor(id?: number,
                userType?: UserType,
                login?: string,
                firstName?: string,
                lastName?: string,
                email?: string,
                activated?: Boolean,
                langKey?: string,
                authorities?: string[],
                password?: string,
                imageUrl?: string) {
        this.id = id ? id : null;
        this.userType = userType ? userType : null;
        this.login = login ? login : null;
        this.firstName = firstName ? firstName : null;
        this.lastName = lastName ? lastName : null;
        this.email = email ? email : null;
        this.activated = activated ? activated : false;
        this.langKey = langKey ? langKey : null;
        this.authorities = authorities ? authorities : null;
        this.password = password ? password : null;
        this.imageUrl = imageUrl ? imageUrl : null;
    }
}
