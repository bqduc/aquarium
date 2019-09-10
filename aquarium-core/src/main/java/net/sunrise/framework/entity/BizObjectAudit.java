/**
 * 
 */
package net.sunrise.framework.entity;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author bqduc
 *
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BizObjectAudit extends ObjectAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5576739490034674563L;

	@CreatedBy
  @Column(updatable = false, name="created_by")
  private String createdBy;

	@LastModifiedBy
  @Column(updatable = false, name="updated_by")
  private String updatedBy;

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
}
