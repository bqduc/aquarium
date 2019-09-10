package net.sunrise.commercial;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.sunrise.commercial.enumeration.AvailabilityDelay;
import net.sunrise.commercial.enumeration.DefaultDocTemplate;
import net.sunrise.commercial.enumeration.IncoTerms;
import net.sunrise.commercial.enumeration.ShippingMethod;
import net.sunrise.commercial.enumeration.Source;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer extends ThirdParty {

	private String customerReference;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;

	private Source source;

	private AvailabilityDelay availabilityDelay;

	private ShippingMethod shippingMethod;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date deliveryDate;

	private IncoTerms incoTerms;

	private DefaultDocTemplate defaultDocTemplate;

	@OneToMany(mappedBy = "customer")
	private List<Contract> contracts;

	@OneToMany(mappedBy = "customer")
	private List<Order> orders;

}
