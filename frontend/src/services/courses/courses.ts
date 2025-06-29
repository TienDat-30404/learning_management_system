import { QueryParams } from "@/types/common";
import apiClient from "../api";

export function getAllCourse(params : QueryParams = {}) {
    return apiClient.get('/gateway/courses', {params})
}