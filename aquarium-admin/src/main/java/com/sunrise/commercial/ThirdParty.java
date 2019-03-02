package com.sunrise.commercial;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import com.sunrise.commercial.enumeration.Currency;
import com.sunrise.commercial.enumeration.PaymentTerms;
import com.sunrise.commercial.enumeration.PaymentTypes;
import com.sunrise.domain.BaseEntity;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ThirdParty extends BaseEntity {

    private String reference;

    private PaymentTerms paymentTerms;

    private PaymentTypes paymentTypes;

    @Lob
    private String publicNote;

    @Lob
    private String privateNote;

    private Currency currency;

    @OneToMany(mappedBy = "thirdParty")
    private List<Contract> contracts;

}
