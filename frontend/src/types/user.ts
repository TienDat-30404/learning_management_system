export interface User {
    id : number,
    fullName : String
    email? : String,
    gender? : String,
    birthDate : String
}

export type UserUpdateData = Partial<User>;
