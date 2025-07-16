import { UserUpdateData } from "@/types/user";
import apiClient from "../api";

export function getDetailUser(idUser : number) {
    return apiClient.get(`/users/${idUser}`)
}

export function updateUser(idUser : number, formData : UserUpdateData) {
    return apiClient.put(`/users/${idUser}`, formData)
}