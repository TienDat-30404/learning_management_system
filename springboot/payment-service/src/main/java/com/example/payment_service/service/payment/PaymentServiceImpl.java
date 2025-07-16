package com.example.payment_service.service.payment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Locale.Category;
import java.util.stream.Collectors;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.payment_service.client.CourseClient;
import com.example.payment_service.client.UserClient;
import com.example.payment_service.config.VnPayConfig;
import com.example.payment_service.dto.ApiResponseDTO;
import com.example.payment_service.dto.course.CourseResponseDTO;
import com.example.payment_service.dto.payment.PaymentRequestDTO;
import com.example.payment_service.dto.payment.PaymentRequestVnpayDTO;
import com.example.payment_service.dto.payment.PaymentResponseDTO;
import com.example.payment_service.dto.user.UserResponseDTO;
import com.example.event.PaymentEvent;
import com.example.payment_service.mapper.PaymentMapper;
import com.example.payment_service.model.Payment;
import com.example.payment_service.model.PaymentMethod;
import com.example.payment_service.repository.PaymentMethodRepository;
import com.example.payment_service.repository.PaymentRepository;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentMapper paymentMapper;
    private final UserClient userClient;
    private final CourseClient courseClient;
    private final PaymentRepository paymentRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;
    private final VnPayConfig vnPayConfig;

    public PaymentResponseDTO createPaymentForCourse(PaymentRequestDTO request, Long userId) {
        Boolean existUser = userClient.checkExistUser(userId);
        if (!existUser) {
            throw new EntityNotFoundException("User not found with id =" + userId);
        }

        Boolean existCourse = courseClient.checkExistCourse(request.getCourseId());
        if (!existCourse) {
            throw new EntityNotFoundException("Course not found with id = " + request.getCourseId());
        }

        Payment payment = paymentMapper.toEntity(request);
    
        payment.setUserId(userId);
        payment.setCourseId(request.getCourseId());

        PaymentMethod paymentMethod = paymentMethodRepository.findById(request.getPaymentMethodId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Payment method not found with id = " + request.getPaymentMethodId()));
        payment.setPaymentMethod(paymentMethod);

        payment = paymentRepository.save(payment);
        PaymentEvent paymentEvent = new PaymentEvent(
                payment.getId(),
                payment.getUserId(),
                payment.getCourseId(),
                "Thanh toán khóa học thành công");
        kafkaTemplate.send("payment-event", paymentEvent);

        // ApiResponseDTO<UserResponseDTO> user = userClient.getUserById(userId);
        // ApiResponseDTO<CourseResponseDTO> course =
        // courseClient.getCourseById(request.getCourseId());
        PaymentResponseDTO response = paymentMapper.toDTO(payment);
        // response.setUser(user.getData());
        // response.setCourse(course.getData());
        return response;
    }

   
    public String taoUrlThanhToan(HttpServletRequest request, PaymentRequestVnpayDTO thanhToanRequest) throws Exception {
        // Tạo ngày giờ hiện tại
        String ngayTao = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String maDonHang = new SimpleDateFormat("ddHHmmss").format(new Date());

        // Lấy địa chỉ IP của client
        String diaChiIp = layDiaChiIp(request);

        // Tạo các tham số VNPay
        Map<String, String> thamSoVnpay = new TreeMap<>();
        thamSoVnpay.put("vnp_Version", "2.1.0");
        thamSoVnpay.put("vnp_Command", "pay");
        thamSoVnpay.put("vnp_TmnCode", vnPayConfig.getTmnCode());
        thamSoVnpay.put("vnp_Locale", "vn");
        thamSoVnpay.put("vnp_CurrCode", "VND");
        thamSoVnpay.put("vnp_TxnRef", maDonHang);
        thamSoVnpay.put("vnp_OrderInfo", thanhToanRequest.getContent());
        thamSoVnpay.put("vnp_OrderType", "order");
        thamSoVnpay.put("vnp_Amount", String.valueOf(thanhToanRequest.getAmount() * 100));
        thamSoVnpay.put("vnp_ReturnUrl", vnPayConfig.getReturnUrl());
        thamSoVnpay.put("vnp_IpAddr", diaChiIp);
        thamSoVnpay.put("vnp_CreateDate", ngayTao);

        // Tạo chuỗi query
        StringBuilder chuoiQuery = new StringBuilder();
        for (Map.Entry<String, String> entry : thamSoVnpay.entrySet()) {
            if (chuoiQuery.length() > 0) {
                chuoiQuery.append("&");
            }
            chuoiQuery.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()))
                     .append("=")
                     .append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
        }

        // Tạo chữ ký HMAC SHA512
        String duLieuKy = chuoiQuery.toString();
        String chuKy = taoChuKyHmacSha512(vnPayConfig.getHashSecret(), duLieuKy);
        chuoiQuery.append("&vnp_SecureHash=").append(chuKy);

        // Tạo URL thanh toán cuối cùng
        return vnPayConfig.getUrl() + "?" + chuoiQuery;
    }


    private String layDiaChiIp(HttpServletRequest request) {
        String diaChiIp = request.getHeader("X-Forwarded-For");
        if (StringUtils.isEmpty(diaChiIp)) {
            diaChiIp = request.getRemoteAddr();
        }
        return diaChiIp;
    }

   
    private String taoChuKyHmacSha512(String khoaBiMat, String duLieu) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA512");
        SecretKeySpec khoa = new SecretKeySpec(khoaBiMat.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        mac.init(khoa);
        byte[] ketQuaKy = mac.doFinal(duLieu.getBytes(StandardCharsets.UTF_8));
        StringBuilder chuoiHex = new StringBuilder();
        for (byte b : ketQuaKy) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                chuoiHex.append('0');
            }
            chuoiHex.append(hex);
        }
        return chuoiHex.toString();
    }


}
