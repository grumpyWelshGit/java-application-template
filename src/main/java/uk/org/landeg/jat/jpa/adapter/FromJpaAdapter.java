package uk.org.landeg.jat.jpa.adapter;

/**
 * 
 * @author andy
 *
 * @param <J> JPA type
 * @param <B> Business object type
 */
public interface FromJpaAdapter<J, B> {
	B fromJpa(J jpa, B business);
}
