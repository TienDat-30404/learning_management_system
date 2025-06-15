package com.example.payment_service.service.payment;

import java.util.Locale.Category;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.payment_service.client.CourseClient;
import com.example.payment_service.client.UserClient;
import com.example.payment_service.dto.ApiResponseDTO;
import com.example.payment_service.dto.course.CourseResponseDTO;
import com.example.payment_service.dto.payment.PaymentRequestDTO;
import com.example.payment_service.dto.payment.PaymentResponseDTO;
import com.example.payment_service.dto.user.UserResponseDTO;
import com.example.event.PaymentEvent;
import com.example.payment_service.mapper.PaymentMapper;
import com.example.payment_service.model.Payment;
import com.example.payment_service.model.PaymentMethod;
import com.example.payment_service.repository.PaymentMethodRepository;
import com.example.payment_service.repository.PaymentRepository;

import jakarta.persistence.EntityNotFoundException;
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

    public PaymentResponseDTO createPaymentForCourse(PaymentRequestDTO request, Long userId) {
        Boolean existUser = userClient.checkExistUser(userId);
        if (!existUser) {
            throw new EntityNotFoundException("User not found with id =" + userId);
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

        ApiResponseDTO<UserResponseDTO> user = userClient.getUserById(userId);
        ApiResponseDTO<CourseResponseDTO> course = courseClient.getCourseById(request.getCourseId());
        PaymentResponseDTO response = paymentMapper.toDTO(payment);
        response.setUser(user.getData());
        response.setCourse(course.getData());
        return response;
    }
}
