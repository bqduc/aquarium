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
package net.brilliance.domain.entity.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import net.brilliance.framework.entity.BizObjectBase;

/**
 * Access right .
 * 
 * @author bqduc
 */
@Entity
@Table(name = "sa_access_right")
public class AccessRight extends BizObjectBase {
	private static final long serialVersionUID = 5474725952032953164L;

	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "name")
	private String name;

	@Column(name = "description", columnDefinition="text")
	private String description;

	public AccessRight() {
		super();
	}

	public AccessRight(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public AccessRight setName(String name) {
		this.name = name;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public AccessRight setDescription(String description) {
		this.description = description;
		return this;
	}

	public static AccessRight createInstance(String name, String description) {
		AccessRight instance = new AccessRight()
				.setName(name)
				.setDescription(description);

		return instance;
	}
}
