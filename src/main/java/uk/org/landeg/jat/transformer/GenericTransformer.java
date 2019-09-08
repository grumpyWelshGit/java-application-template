package uk.org.landeg.jat.transformer;

public interface GenericTransformer<F,T> {
	T apply(F from, T to);
	
	T apply(F from);
}
