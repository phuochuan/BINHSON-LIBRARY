package com.library.binhson.paymentservice.rest;

import com.library.binhson.paymentservice.dto.BaseResponse;
import com.library.binhson.paymentservice.dto.UrlResponse;
import com.library.binhson.paymentservice.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PaypalController {

    private final PaypalService paypalService;



    @PostMapping("/paypal/creation")
    public ResponseEntity<?> createPayment(){
        try{
            String cancelUrl="http://localhost:8080/paypal/cancel";
            String successUrl="http://localhost:8080/paypal/success";
            Payment payment=paypalService.createPayment(
                    0.01, "USD", "paypal"
                            ,"sale",cancelUrl, "", successUrl
            );

            for(Links links: payment.getLinks()){
                if(links.getRel().equals("approval_url")){
                    return ResponseEntity.ok(BaseResponse.builder()
                            .data(new UrlResponse(links.getHref())).dateAt(new Date())
                            .build());
                }
            }
        }catch (PayPalRESTException ex){
            ex.printStackTrace();
            log.error(ex.toString());
        }
        return  ResponseEntity.status(500).body(BaseResponse.builder()
                .message("Server have problem").dateAt(new Date())
                .build());
    }

    @GetMapping("/paypal/success")
    public ResponseEntity paymentSuccess(@RequestParam("paymentId") String paymentId,
                                 @RequestParam("payerId") String payerId){
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            for(Links links: payment.getLinks()){
                if(links.getRel().equals("approval_url"))
                    return ResponseEntity.ok("");
            }
        }catch (Exception ex){
            log.error(ex.toString());
        }
        return ResponseEntity.status(500).body(BaseResponse.builder().message("Server have error. Don't worry, we are going to refund for you earliest.")
                .build());

    }

    @GetMapping("/paypal/cancel")
    public ResponseEntity<?> paymentCancel(){
        return ResponseEntity.status(200).body(BaseResponse.builder().message("Your transaction was destroyed.")
                .build());

    }


}
