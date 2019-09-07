package uk.org.landeg.jat.jpa.adapter;

/**
 * 
 * @author andy
 *
 * @param <J> JPA type
 * @param <B> Business type
 */
public interface ToJpaAdatper<J, B> {
	J toJpa(B business, J jpa);
}
