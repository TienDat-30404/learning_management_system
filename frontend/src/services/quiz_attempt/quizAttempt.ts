import { QuizAttempt } from "@/types/quizAttempt";
import apiClient from "../api";
import { LessonProgress } from "@/types/lessonProgress";


export function fetchCompletedQuizQuestion(formData : QuizAttempt) {
    return apiClient.post('/quiz-attempts', formData)
}

export function fetchHistoryQuizOfUser(idQuiz : number) {
    console.log("2222222", idQuiz)
    return apiClient.get(`/quiz-attempts/history/${idQuiz}`)
}