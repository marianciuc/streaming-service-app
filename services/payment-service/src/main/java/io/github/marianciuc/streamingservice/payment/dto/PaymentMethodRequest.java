package io.github.marianciuc.streamingservice.payment.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

@Validated
public record PaymentMethodRequest(

        @NotEmpty(message = "Name should not be empty")
        String name,

        @Pattern(regexp = "\\b(?:4[0-9]{12}(?:[0-9]{3})?|(?:5[1-5]\\d{2}|222[1-9]|22[3-9]\\d|2[3-6]\\d{2}|27[01]\\d|2720)\\d{12}|3[47]\\d{13}|3(?:0[0-5]|[68]\\d)\\d{11}|6(?:011|5[0-9]{2})\\d{12}|(?:2131|1800|35\\d{3})\\d{11})\\b", message = "Invalid card number")
        @NotEmpty
        String cardNumber,

        @NotEmpty(message = "Card brand should not be empty")
        String cardBrand,

        @Pattern(regexp = "(0[1-9]|1[0-2])", message = "Must be a valid month (MM)")
        @NotEmpty
        String cardExpiryMonth,

        @Pattern(regexp = "([0-9]{4})", message = "Must be a valid year (YYYY)")
        @NotEmpty
        String cardExpiryYear,

        @Size(min = 3, max = 3, message = "Must be exactly 3 digits")
        @NotEmpty
        String cardCVV
) {
}
