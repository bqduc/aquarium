package net.sunrise.commercial;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.sunrise.commercial.enumeration.Currency;
import net.sunrise.commercial.enumeration.PaymentTerms;
import net.sunrise.commercial.enumeration.PaymentTypes;
import net.sunrise.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

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
