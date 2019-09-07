package uk.org.landeg.jat.jpa.domain;

import java.io.Serializable;
import java.util.Properties;
import java.util.UUID;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

public class UuidGenerator implements Configurable, IdentifierGenerator{

	@Override
	public Serializable generate(SharedSessionContractImplementor arg0, Object arg1) {
		return UUID.randomUUID().toString();
	}

	@Override
	public void configure(Type arg0, Properties arg1, ServiceRegistry arg2) {
		// unused
	}

}
