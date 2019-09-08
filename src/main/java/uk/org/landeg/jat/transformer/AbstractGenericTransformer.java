package uk.org.landeg.jat.transformer;

import org.springframework.beans.BeanUtils;

/**
 * Unidirectional type transformer.
 * 
 * @author andy
 *
 * @param <F> from type (source)
 * @param <T> to type (target)
 */
public abstract class AbstractGenericTransformer<F,T> implements GenericTransformer<F, T>{
	private final Class<T> toType;

	public AbstractGenericTransformer(Class<T> toType) {
		super();
		this.toType = toType;
	}

	/**
	 * Returns an instance of the target type with data mapped from the source.
	 */ 
	@Override
	public T apply(F from, T to) {
		if (to == null) {
			to = newInstance(toType);
		}
		BeanUtils.copyProperties(from, to);
		this.copyAdditionalProperties(from, to);

		return to;
	}

	/**
	 * Returns an instance of the target type with data mapped from the source.
	 * 
	 * This implmentation will generate a new instance of the target type.
	 */
	@Override
	public T apply(F from) {
		return this.apply(from, null);
	}

	/**
	 * Copy additional properties not covered by {@link BeanUtils#copyProperties(Object, Object)}
	 *
	 * @param from source object
	 * @param to target object
	 * @return target object;
	 */
	protected T copyAdditionalProperties(F from, T to) {
		return to;
	}

	private <X> X newInstance (Class<X> type) {
		try {
			return type.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
