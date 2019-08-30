package com.sunrise.stock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import com.sunrise.domain.BaseEntity;
import com.sunrise.stock.enumeration.*;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Created on January, 2018
 *
 * @author bqduc
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Service extends BaseEntity {

	@NotEmpty
	private String reference;

	@NotEmpty
	private String label;

	private SaleStatus saleStatus;

	private PurchaseStatus purchaseStatus;

	@Lob
	private String description;

	@URL
	private String publicUrl;

	private Integer durationValue;

	private DurationType durationType;

	@Lob
	private String note;

	@ManyToMany
	private List<Tag> tags;

	private Double salePrice;

	private TaxType taxType;

	private Double minSalePrice;

	private TaxRate taxRate;

}
