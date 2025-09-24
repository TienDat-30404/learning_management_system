import { QueryParams } from "@/types/common";
import apiClient from "../api";

export function fetchDataDashboardTeacher(params : QueryParams = {}) {
    return apiClient.get('/aggregated/teacher/dashboard', {params})
}

export function fetchDataStudentsOfTeacher(params : QueryParams = {}) {
    return apiClient.get('/aggregated/teacher/list-students', {params})
}

