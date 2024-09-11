package io.github.marianciuc.streamingservice.payment.entity;

import io.github.marianciuc.streamingservice.payment.enums.CardStatus;
import io.github.marianciuc.streamingservice.payment.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "cardholders")
public class CardHolder {
    @Id
    @Column(name = "id")
    private UUID userId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @Column(name = "stripe_customer_id")
    private String stripeCustomerId;

    @Column(name = "email")
    private String email;

    @Column(name = "card_holder_name")
    private String cardHolderName;

    @Column(name = "phone")
    private String phoneNumber;

    @Column(name = "card_status")
    @Enumerated(EnumType.STRING)
    private CardStatus cardStatus;

    @OneToMany(mappedBy = "cardHolder", cascade = CascadeType.ALL)
    private List<Transaction> transactions;
}
