// src/services/apiClient.ts
import axios from 'axios';

const apiClient = axios.create({
  baseURL: process.env.NEXT_PUBLIC_API_BASE_URL
});

apiClient.interceptors.request.use(config => {
  
  const token = localStorage.getItem('accessToken');
  console.log("token" + token)
  if (token && config.headers) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
}, error => {
  return Promise.reject(error);
});

// apiClient.interceptors.response.use(
//   response => response,
//   error => {
//     if (error.response) {
//       if (error.response.status === 401) {
//         // Xử lý logout hoặc refresh token
//         // localStorage.removeItem('token');
//         // window.location.href = '/login';
//       }
//     }
//     return Promise.reject(error);
//   }
// );

export default apiClient;
