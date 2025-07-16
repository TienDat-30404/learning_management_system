import { QueryParams } from "@/types/common";
import apiClient from "../api";

export function getTotalLessonCompleted(params : QueryParams = {}) {
    return apiClient.get('lesson-progress/total-lesson-completed', {params})
}