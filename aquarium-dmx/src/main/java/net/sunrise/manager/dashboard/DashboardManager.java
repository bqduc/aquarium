/**
 * 
 */
package net.sunrise.manager.dashboard;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import net.sunrise.domain.entity.contact.ClientProfile;
import net.sunrise.hrcx.manager.EmployeeManager;
import net.sunrise.manager.catalog.CatalogManager;
import net.sunrise.manager.contact.ClientProfileManager;
import net.sunrise.service.api.contact.ContactService;
import net.sunrise.service.api.inventory.ProductService;

/**
 * @author bqduc
 *
 */
//@Service
@Scope(value = "singleton")
@Component
public class DashboardManager {
	private final static long INITIAL_TOTAL = 0;

	/*@Inject 
	private BookService bookService;*/

	@Inject 
	private ProductService productService;

	@Inject 
	private ContactService contactService;

	@Inject 
	private EmployeeManager employeeManager;

	@Inject 
	private ClientProfileManager clientProfileManager;
/*
	@Inject 
	private VbbForumManager forumManager;*/

	@Inject 
	private CatalogManager catalogManager;

	private Map<String, Object> dashboardDataMap = new HashMap<>();

	/*public long getTotalForums(){
		return forumManager.count();
	}*/

	public long getTotalClientProfiles(){
		return clientProfileManager.count();
	}

	public long getTotalOrders(ClientProfile clientProfile){
		return INITIAL_TOTAL;
	}

	public Map<String, Object> getDashboardDataMap() {
		return dashboardDataMap;
	}

	public void setDashboardDataMap(Map<String, Object> dashboardDataMap) {
		this.dashboardDataMap = dashboardDataMap;
	}

	public Map<?, ?> syncData(){
		this.dashboardDataMap.put("totalClientProfiles", clientProfileManager.count());
		this.dashboardDataMap.put("contacts", contactService.count());
		this.dashboardDataMap.put("employees", employeeManager.count());
		this.dashboardDataMap.put("catalogues", catalogManager.count());
		this.dashboardDataMap.put("productCount", productService.count());
		//this.dashboardDataMap.put("bookCount", bookService.count());
		return this.dashboardDataMap;
	}
}
