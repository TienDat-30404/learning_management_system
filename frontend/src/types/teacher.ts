import { Category } from "./category"

export interface DashboardTeacher {
    id: number,
    title: String,
    description: string,
    image: String,
    totalLesson: number,
    quantityStudent: number,
    category : Category
    totalQuizs : number
}

