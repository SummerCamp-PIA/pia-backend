package com.pia.reservation.service;

import com.pia.reservation.dto.request.PaymentRequest;
import com.pia.reservation.dto.request.SubDto.CreditCardDto;
import com.pia.reservation.dto.response.PaymentResponse;
import com.pia.reservation.model.CreditCard;
import com.pia.reservation.model.Payment;
import com.pia.reservation.repository.CreditCardRepository;
import com.pia.reservation.repository.PaymentRepository;
import com.pia.reservation.service.Email.EmailService;
import com.pia.reservation.util.ModelMapperUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class PaymentService {


    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private EmailService emailService;



    // Json Path kullanabilir mi araştır.
    public String saveDummyCards(List<CreditCardDto> dummyCreditCards){

        for(CreditCardDto cardDto : dummyCreditCards){

            CreditCard creditCard = ModelMapperUtil.modelMapper.map(cardDto, CreditCard.class);

            Random random = new Random();

            int randomCardAmount = random.nextInt(0,100000);
            creditCard.setAmount(randomCardAmount);

            creditCardRepository.save(creditCard);



        }

        return "Dummy cards has been saved succesfully";
    }


    public Boolean makePayment(PaymentRequest paymentRequest) throws MessagingException {

        CreditCard creditCard = creditCardRepository.findByCardNo(paymentRequest.getCreditCardDto().getCardNo())
                .orElseThrow(() -> new RuntimeException("Kredi kartı bulunamadı"));
        if(paymentRequest.getPrice()<=creditCard.getAmount()){

            int newAmount = creditCard.getAmount() - paymentRequest.getPrice();
            creditCard.setAmount(newAmount);
            creditCardRepository.save(creditCard);

            Payment payment = new Payment();
            payment.setCreditCard(creditCard);
            payment.setHotelName(paymentRequest.getHotelName());
            payment.setUserName("USER");
            paymentRepository.save(payment);

            System.out.println("PAYMENT THREAD : " + Thread.currentThread().getName());

            emailService.sendEmail("erincak2+e@gmail.com", "Pia Hotels" , "Erinç Ak", "142");

            return true;
        }
        else{
            return false;
        }
    }
}
