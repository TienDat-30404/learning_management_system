import { QueryParams } from "@/types/common";
import apiClient from "../api";


export function getCourseProgressOfUser(params : QueryParams = {}) {
    return apiClient.get('/gateway/enrollments', {params})
}