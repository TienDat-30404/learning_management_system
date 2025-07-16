import { User } from "./user";

export interface Review {
    id : number,
    comment : String,
    rating : number,
    user : User
    createdAt : string
}