/**
 * 
 */
package net.sunrise.cdix.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import net.sunrise.framework.entity.BizObjectAudit;

/**
 * @author bqduc
 *
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reserve_resource")
public class ReserveResource extends BizObjectAudit/*BizObjectBase*/ {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5818404310881647902L;

	@Column(name = "name", nullable = false, length=256)
	private String name;

	@Column(name = "type", nullable = false, length=100)
	private String type;

	@Lob
	@Column(name="data")
	//@Basic(fetch=FetchType.LAZY)
	@Type(type="org.hibernate.type.BinaryType")
  private byte[] data;

	@Column(name = "description", columnDefinition="TEXT")
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String fileType) {
		this.type = fileType;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
