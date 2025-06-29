// src/services/authService.ts
import apiClient from '../api';
import { LoginFormData, RegisterFormData } from '@/types/auth';

export function login(formData: LoginFormData) {
  return apiClient.post('/auth/login', formData);
}

export function register(formData : RegisterFormData) {
  return apiClient.post('/auth/register', formData)
}
