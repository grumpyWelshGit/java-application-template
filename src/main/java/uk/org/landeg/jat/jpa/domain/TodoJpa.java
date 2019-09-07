package uk.org.landeg.jat.jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class TodoJpa extends AbstractDomainObject {
	@Column(name="message", length=100)
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
