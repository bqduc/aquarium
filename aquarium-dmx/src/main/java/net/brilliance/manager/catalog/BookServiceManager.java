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
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brilliance.common.CommonConstants;
import net.brilliance.domain.entity.general.RefBook;
import net.brilliance.framework.manager.AbstractServiceManager;
import net.brilliance.framework.repository.JBaseRepository;
import net.brilliance.repository.general.catalog.BookRefRepository;

/**
 * Book service implementation. Provides implementation of the department
 * 
 * @author bqduc
 *
 */
@Service
@Transactional
public class BookServiceManager extends AbstractServiceManager<RefBook, Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6005224094134853078L;
	private final static int IDX_LICENSE = 0;
	private final static int IDX_ISSUE_DATE = 1;
	private final static int IDX_INVESTOR = 2;
	private final static int IDX_NAME = 3;
	private final static int IDX_INVESTOR_COUNTRY = 4;
	private final static int IDX_BUSINESS_DOMAIN = 5;
	private final static int IDX_INVESTMENT_CAPITAL = 6;
	private final static int IDX_IMPLEMENT_ADDRESS = 7;
	private final static int IDX_DURATION = 8;
	private final static int IDX_CONTACT_ADDRESS = 10;
	
	@Inject
	private BookRefRepository repository;

	@Override
	protected JBaseRepository<RefBook, Long> getRepository() {
		return this.repository;
	}

	public List<RefBook> createDummyObjects() {
		List<RefBook> fetchedObjects = importFromExcel();
		for (RefBook fetchedObject :fetchedObjects) {
			try {
				this.save(fetchedObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return fetchedObjects;
		/*Book fetchObject = null;
		List<Book> fetchedObjects = new ArrayList<>();
		for (int i = 1; i <= CommonUtility.MAX_DUMMY_OBJECTS; ++i){
			fetchObject = new Book();
			this.save(fetchObject);
			fetchedObjects.add(fetchObject);
		}
		return fetchedObjects;*/
	}

	public Optional<RefBook> getByName(String name) {
		return repository.findByName(name);
	}

	public Optional<RefBook> getByIsbn(String isbn) {
		return repository.findByIsbn(isbn);
	}

	public List<RefBook> getAll(Integer pageNumber){
    PageRequest pageRequest = PageRequest.of(pageNumber - 1, CommonConstants.DEFAULT_PAGE_SIZE, Sort.Direction.ASC, "id");
    Page<RefBook> pagedBooks = getRepository().findAll(pageRequest);
    return pagedBooks.getContent();
	}

	private List<RefBook> importFromExcel(){
		List<RefBook> projects = new ArrayList<>();
		return projects;
	}
}
