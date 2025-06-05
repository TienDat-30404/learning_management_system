package com.example.payment_service.dto.paymentMethod;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PaymentMethodRequestDTO {
    @NotBlank(message = "Tên phương thức thanh toán không được để trống")
    private String name;
}
