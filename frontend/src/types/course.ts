import { Category } from "./category"
import { Discount } from "./discount"
import { User } from "./user"


export interface Course {
    id : number,
    title : string,
    image : string,
    category : Category,
    price : number,
    user : User,
    discount : Discount
}