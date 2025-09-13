import { Quiz } from "./quiz"

export interface QuizAttempt {
    quizId : number,
    score : number,
    duration : number
}

export interface QuizResult {
    id : number,
    quiz : Quiz,
    score : number,
    duration : number
    createdAt : Date
}