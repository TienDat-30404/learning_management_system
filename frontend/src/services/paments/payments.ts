import { Payment, PaymentVnpay } from '@/types/payments';
import apiClient from '../api';


export function handlePayment(formData: Payment) {
  return apiClient.post('/payments', formData);
}



export function createVnpayPaymentUrl(formData: PaymentVnpay) {
  return apiClient.post('/payments/process-vnpay', formData);
}

