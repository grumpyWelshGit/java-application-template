package uk.org.landeg.jat.api.model.adapter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import uk.org.landeg.jat.annotation.qualifier.RestFromAdapterType;
import uk.org.landeg.jat.api.model.TodoRequestModel;
import uk.org.landeg.jat.objects.TodoInternal;

@Component
@RestFromAdapterType(TodoRequestModel.class)
public class TodoRequestAdapter implements FromRestAdapter<TodoRequestModel, TodoInternal>{

	@Override
	public TodoInternal fromRest(TodoRequestModel rest) {
		return this.fromRest(rest, null);
	}

	@Override
	public TodoInternal fromRest(TodoRequestModel rest, TodoInternal internal) {
		if (internal == null) {
			internal = new TodoInternal();
		}
		BeanUtils.copyProperties(rest, internal);
		return internal;
	}
}
