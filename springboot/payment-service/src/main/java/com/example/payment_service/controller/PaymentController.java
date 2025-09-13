package com.example.payment_service.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.payment_service.context.AuthenticatedUser;
import com.example.payment_service.dto.ApiResponseDTO;
import com.example.payment_service.dto.payment.PaymentRequestDTO;
import com.example.payment_service.dto.payment.PaymentRequestVnpayDTO;
import com.example.payment_service.dto.payment.PaymentResponseDTO;
import com.example.payment_service.service.payment.PaymentService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
@Controller
public class PaymentController {

    private final PaymentService paymentService;
    private final AuthenticatedUser authenticatedUser;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<PaymentResponseDTO>> createPaymentForCouse(
            @Valid @RequestBody PaymentRequestDTO request) {
        Long userId = authenticatedUser.getUserId();
        PaymentResponseDTO payment = paymentService.createPaymentForCourse(request, userId);
        ApiResponseDTO<PaymentResponseDTO> response = new ApiResponseDTO<>(
                201, payment, "Create payment for course successful");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/process-vnpay")
    public ResponseEntity<Map<String, String>> taoUrlThanhToan(
            HttpServletRequest request,
            @RequestBody PaymentRequestVnpayDTO thanhToanRequest) {
        try {
            String urlThanhToan = paymentService.taoUrlThanhToan(request, thanhToanRequest);
            Map<String, String> phanHoi = new HashMap<>();
            phanHoi.put("vnpUrl", urlThanhToan);
            return ResponseEntity.ok(phanHoi);
        } catch (Exception e) {
            Map<String, String> loi = new HashMap<>();
            loi.put("message", "Không thể tạo URL thanh toán: " + e.getMessage());
            return ResponseEntity.status(500).body(loi);
        }
    }

}


/*
    - VNPAY : 
        NCB
        9704198526191432198
        NGUYEN VAN A   
        07/15
        123456 

    - MOMO : 
        name : NGUYEN VAN A
        stk : 5200 0000 0000 1096 (số thể đúng) , 5200 0000 0000 1104 (số thẻ sai)
        05/25	
        cvc : 111
        otp : 1234

    - ZALO pay : 
        Số thẻ : 4111111111111111
        Tên : 	NGUYEN VAN A
        Ngày hết hạn : 	01/25
        Mã CVV	123


*/