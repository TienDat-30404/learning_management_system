import { QueryParams } from "@/types/common";
import apiClient from "../api";

export function getAllPaymentMethods() {
    return apiClient.get('/paymentMethods')
}

