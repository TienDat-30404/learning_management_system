// src/services/authService.ts
import api from '../api';
import { LoginFormData, RegisterFormData } from '@/types/auth';

export function login(formData: LoginFormData) {
  return api.post('/auth/login', formData);
}

export function register(formData : RegisterFormData) {
  return api.post('/auth/register', formData)
}
