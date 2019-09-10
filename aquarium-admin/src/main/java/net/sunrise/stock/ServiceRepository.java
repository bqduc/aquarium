package net.sunrise.stock;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.sunrise.stock.enumeration.PurchaseStatus;
import net.sunrise.stock.enumeration.SaleStatus;

import java.util.List;

@Repository
public interface ServiceRepository extends CrudRepository<Service, Long>{

	List<Service> findBySaleStatusAndPurchaseStatus(SaleStatus saleStatus, PurchaseStatus purchaseStatus);

	int countBySaleStatusAndPurchaseStatus(SaleStatus saleStatus, PurchaseStatus purchaseStatus);

	List<Service> findBySaleStatus(SaleStatus saleStatus);

}
