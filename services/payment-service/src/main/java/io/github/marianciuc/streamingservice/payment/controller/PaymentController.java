package io.github.marianciuc.streamingservice.payment.controller;

import io.github.marianciuc.streamingservice.payment.dto.common.AddressDto;
import io.github.marianciuc.streamingservice.payment.dto.common.CardHolderDto;
import io.github.marianciuc.streamingservice.payment.dto.requests.CreateCartHolderRequest;
import io.github.marianciuc.streamingservice.payment.dto.requests.UpdateCardHolderRequest;
import io.github.marianciuc.streamingservice.payment.entity.JWTUserPrincipal;
import io.github.marianciuc.streamingservice.payment.service.AddressService;
import io.github.marianciuc.streamingservice.payment.service.CardHolderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final CardHolderService cardHolderService;
    private final AddressService addressService;

    @PostMapping("/card-holder")
    public ResponseEntity<CardHolderDto> createCardHolder(@Valid @RequestBody CreateCartHolderRequest request) {
        return ResponseEntity.ok(cardHolderService.createCardHolder(request));
    }

    @PutMapping("/card-holder")
    public ResponseEntity<Void> updateCardHolder(@RequestBody UpdateCardHolderRequest request) {
        cardHolderService.updateCardHolder(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/address")
    public ResponseEntity<Void> updateAddress(@RequestBody AddressDto request) {
        addressService.updateAddress(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/payment-method")
    public ResponseEntity<Void> updatePaymentMethod(@RequestParam("token") String token) {
        cardHolderService.updatePaymentMethod(token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/card-holder")
    public ResponseEntity<CardHolderDto> getCardHolder(@RequestParam(value = "cardHolderId", required = false) UUID cardHolderId,
                                                       Authentication authentication) {
        if (authentication.getPrincipal() instanceof JWTUserPrincipal jwtUserPrincipal) {
            if (jwtUserPrincipal.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
                UUID idToFetch = (cardHolderId != null) ? cardHolderId : jwtUserPrincipal.getUserId();
                return ResponseEntity.ok(cardHolderService.getCardHolder(idToFetch));
            } else {
                return ResponseEntity.ok(cardHolderService.getCardHolder(jwtUserPrincipal.getUserId()));
            }
        }
        throw new AccessDeniedException("Access denied");
    }
}
