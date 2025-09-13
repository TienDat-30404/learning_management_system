import { QueryParams } from "@/types/common";
import apiClient from "../api";
import { LessonProgress } from "@/types/lessonProgress";

export function getTotalLessonCompleted(params : QueryParams = {}) {
    return apiClient.get('lesson-progress/total-lesson-completed', {params})
}

export function fetchCompletedLesson(formData : LessonProgress) {
    return apiClient.post('/lesson-progress', formData)
}