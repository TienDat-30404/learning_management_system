import { createVnpayPaymentUrl, handlePayment } from "@/services/paments/payments";
import { Payment, PaymentVnpay, PaymentVnpayError, PaymentVnpaySuccess } from "@/types/payments";
import { useMutation } from "@tanstack/react-query";

export function useProcessPaymentVnpay() {
  return useMutation<PaymentVnpaySuccess, PaymentVnpayError, PaymentVnpay>({
    mutationFn: async (body: PaymentVnpay) => {
      const response = await createVnpayPaymentUrl(body);
      return response.data;
    },
    onSuccess: (data) => {
      console.log("VNPay URL received:", data.vnpUrl);
      window.location.href = data.vnpUrl; 
    },
    onError: (error) => {
      console.error("Payment initiation failed:", error.message);
      alert("Khởi tạo thanh toán thất bại: " + error.message);
    },
  });
}


export function useHandlePayment() {
  return useMutation<Payment, Error, Payment>({
    mutationFn: async (body: Payment) => {
      const response = await handlePayment(body);
      return response.data;
    },
  });
}