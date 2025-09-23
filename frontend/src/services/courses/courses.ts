import { QueryParams } from "@/types/common";
import apiClient from "../api";

export function getAllCourse(params : QueryParams = {}) {
    return apiClient.get('/aggregated/courses', {params})
}

export function getDetailCourse(id : number) {
    return apiClient.get(`/aggregated/courses/${id}`)
}