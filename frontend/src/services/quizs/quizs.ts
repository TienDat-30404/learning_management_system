import { QueryParams } from "@/types/common";
import apiClient from "../api";

export function checkExistLessonQuiz(lessonId : number) {
    return apiClient.get(`/quizs/check-lesson/${lessonId}`)
}

export function getAllQuestionOfQuiz(params : QueryParams = {}) {
    return apiClient.get('/quizs', {params})
}