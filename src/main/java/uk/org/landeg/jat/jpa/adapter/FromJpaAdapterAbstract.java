package uk.org.landeg.jat.jpa.adapter;

import org.springframework.beans.BeanUtils;

public class FromJpaAdapterAbstract<J, B> implements FromJpaAdapter<J, B> {
	private final Class<J> jpaType;
	private final Class<B> internalType;
	
	public FromJpaAdapterAbstract(Class<J> jpaType, Class<B> internalType) {
		super();
		this.jpaType = jpaType;
		this.internalType = internalType;
	}

	@Override
	public B fromJpa(J jpa, B business) {
		if (business == null) {
			try {
				business = internalType.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		BeanUtils.copyProperties(jpa, business);
		return business;
	}
}
