package uk.org.landeg.jat.api.model.adapter;

public interface ToRestAdapter<R,I> {
	public R toModel (I internal);
}
