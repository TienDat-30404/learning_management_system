import { Course } from "./course";

export interface Lesson {
    id : number,
    title : string,
    course : Course,
    content : string,
    videoUrl : string,
    isCompleted : boolean
}