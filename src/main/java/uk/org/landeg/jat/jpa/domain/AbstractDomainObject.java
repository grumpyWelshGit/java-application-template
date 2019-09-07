package uk.org.landeg.jat.jpa.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public class AbstractDomainObject {
	@Id
	@Column(name="id")
	@GeneratedValue(generator="uuid-generator")
	@GenericGenerator(name="uuid-generator", strategy="uk.org.landeg.jat.jpa.domain.UuidGenerator")
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	
}
