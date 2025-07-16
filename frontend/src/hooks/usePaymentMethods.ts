import { useQuery } from "@tanstack/react-query";
import { QueryParams } from "@/types/common";
import { getAllPaymentMethods } from "@/services/payment_methods/paymentMethods";

export function useGetAllPaymentMethods() {
  return useQuery({
    queryKey: ["payment_methods"],
    queryFn: () => getAllPaymentMethods().then(res => res.data)
  });
}
