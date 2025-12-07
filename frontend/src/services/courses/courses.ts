import { QueryParams } from "@/types/common";
import apiClient from "../api";
import { UpdateCourse } from "@/types/course";

export function getAllCourse(params : QueryParams = {}) {
    return apiClient.get('/aggregated/courses', {params})
}

export function getDetailCourse(id : number) {
    return apiClient.get(`/aggregated/courses/${id}`)
}

export function updateCourse(idCourse : number, formData : FormData) {
    return apiClient.put(`/courses/${idCourse}`, formData)
}