package com.sunrise.commercial;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.sunrise.commercial.enumeration.AvailabilityDelay;
import com.sunrise.commercial.enumeration.DefaultDocTemplate;
import com.sunrise.commercial.enumeration.IncoTerms;
import com.sunrise.commercial.enumeration.ShippingMethod;
import com.sunrise.commercial.enumeration.Source;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
