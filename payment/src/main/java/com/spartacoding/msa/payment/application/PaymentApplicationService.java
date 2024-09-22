package com.spartacoding.msa.payment.application;

import com.spartacoding.msa.payment.domain.Payment;
import com.spartacoding.msa.payment.domain.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentApplicationService {

    Logger logger = LoggerFactory.getLogger(PaymentApplicationService.class);
    private final PaymentRepository paymentRepository;
    private final EventApplicationService eventApplicationService;

    // TODO PG연동은 스킵. 주문생성에 대한 결제 완료처리, 결제완료에 대한 이벤트 발행

    @Transactional
    public void completePayment(Long id, BigDecimal totalPrice) {
        
        logger.info("결제");
        Payment payment = new Payment(id, totalPrice);
        payment = paymentRepository.save(payment);

        payment.complete();
        paymentRepository.save(payment);

        eventApplicationService.publishPaymentCompletedEvent(payment.createPaymentCompletedEvent());
    }
}
