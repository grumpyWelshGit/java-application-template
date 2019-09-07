package uk.org.landeg.jat.api.model.adapter;

public interface FromRestAdapter<R, I> {
	I fromRest(R rest);
	I fromRest(R rest, I internal);
}
