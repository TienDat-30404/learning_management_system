import { QueryParams } from "@/types/common";
import apiClient from "../api";


export function getAllCategory(params : QueryParams = {}) {
    return apiClient.get('/categories', {params})
}