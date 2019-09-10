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
package net.sunrise.manager.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sunrise.common.CommonConstants;
import net.sunrise.common.CommonUtility;
import net.sunrise.domain.entity.general.Project;
import net.sunrise.framework.logging.LogService;
import net.sunrise.framework.manager.AbstractServiceManager;
import net.sunrise.framework.repository.JBaseRepository;
import net.sunrise.model.base.IDataContainer;
import net.sunrise.repository.general.catalog.ProjectRepository;

/**
 * Project service implementation. Provides implementation of the project
 * 
 * @author bqduc
 *
 */

@Service
@Transactional
public class ProjectServiceManager extends AbstractServiceManager<Project, Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7294531852060556543L;

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
	private final static int IDX_INVESTMENT_MODEL = 11;

	@Inject 
	private LogService log;
	
	@Inject
	private ProjectRepository repository;

	@Override
	protected JBaseRepository<Project, Long> getRepository() {
		return this.repository;
	}

	public List<Project> createDummyObjects() {
		List<Project> fetchedObjects = importFromExcel();
		for (Project fetchedObject :fetchedObjects) {
			try {
				this.save(fetchedObject);
			} catch (Exception e) {
				log.error(CommonUtility.getStackTrace(e));
			}
		}
		return fetchedObjects;
	}

	public Optional<Project> getByName(String name) {
		return repository.findByName(name);
	}

	public Optional<Project> getByCode(String license) {
		return repository.findByCode(license);
	}

	public List<Project> getAll(Integer pageNumber){
    PageRequest pageRequest = PageRequest.of(pageNumber - 1, CommonConstants.DEFAULT_PAGE_SIZE, Sort.Direction.ASC, "id");
    Page<Project> pagedProjects = getRepository().findAll(pageRequest);
    return pagedProjects.getContent();
	}

	private List<Project> importFromExcel(){
		List<Project> projects = new ArrayList<>();
		
		return projects;
	}

	private IDataContainer getDataFromCsv(){
		return null;
	}
}
