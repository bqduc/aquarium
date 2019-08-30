/*
* Copyright 2017, Bui Quy Duc
* by the @authors tag. See the LICENCE in the distribution for a
* full listing of individual contributors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package net.brilliance.manager.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brilliance.common.CommonConstants;
import net.brilliance.common.CommonUtility;
import net.brilliance.domain.entity.stock.Product;
import net.brilliance.framework.logging.LogService;
import net.brilliance.framework.manager.AbstractServiceManager;
import net.brilliance.framework.repository.JBaseRepository;
import net.brilliance.repository.general.ProductRepositoryOrigin;

/**
 * Project service implementation. Provides implementation of the department
 * 
 * @author bqduc
 *
 */

@Service
@Transactional
public class ProductServiceManager extends AbstractServiceManager<Product, Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7288539202022009702L;

	private final static int IDX_CODE = 0;
	private final static int IDX_NAME = 3;
	private final static int IDX_LONG_NAME = 3;
	private final static int IDX_TRANSLATED_NAME = 2;
	private final static int IDX_ISSUE_DATE = 1;
	private final static int IDX_INVESTOR_COUNTRY = 4;
	private final static int IDX_BUSINESS_DOMAIN = 5;
	private final static int IDX_INVESTMENT_CAPITAL = 6;
	private final static int IDX_IMPLEMENT_ADDRESS = 7;
	private final static int IDX_DURATION = 8;	
	private final static int IDX_CONTACT_ADDRESS = 10;
	private final static int IDX_INVESTMENT_MODEL = 11;
	
	@Inject 
	private LogService log;

	@Inject
	private ProductRepositoryOrigin repository;

	@Override
	protected JBaseRepository<Product, Long> getRepository() {
		return this.repository;
	}

	public List<Product> createDummyObjects() {
		List<Product> fetchedObjects = importFromExcel();
		for (Product fetchedObject :fetchedObjects) {
			try {
				this.save(fetchedObject);
			} catch (Exception e) {
				log.error(CommonUtility.getStackTrace(e));
			}
		}
		return fetchedObjects;
	}

	public Product getByName(String name) {
		return repository.findByName(name);
	}

	public Product getByCode(String license) {
		return repository.findByCode(license);
	}

	public List<Product> getAll(Integer pageNumber){
    PageRequest pageRequest = PageRequest.of(pageNumber - 1, CommonConstants.DEFAULT_PAGE_SIZE, Sort.Direction.ASC, "id");
    Page<Product> pagedProducts = getRepository().findAll(pageRequest);
    return pagedProducts.getContent();
	}

	private List<Product> importFromExcel(){
		List<Product> projects = new ArrayList<>();
		return projects;
	}
}
