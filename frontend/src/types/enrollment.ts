import { Course } from "./course";
import { User } from "./user";

export interface Enrollment {
    id : number,
    progress : number,
    user : User,
    course : Course,
    
}