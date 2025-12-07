import { QueryParams } from "@/types/common";
import apiClient from "../api";


export function getAllLesson(params : QueryParams = {}) {
    return apiClient.get('/aggregated/lesson_progress', {params})
}

export function createLesson(formData : FormData) {
    return apiClient.post(`/lessons`, formData)
}