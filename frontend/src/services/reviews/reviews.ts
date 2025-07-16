import { QueryParams } from "@/types/common";
import apiClient from "../api";

export function getAllReview(params : QueryParams = {}) {
    return apiClient.get('/reviews', {params})
}

