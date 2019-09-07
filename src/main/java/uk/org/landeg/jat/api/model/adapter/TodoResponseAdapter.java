package uk.org.landeg.jat.api.model.adapter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import uk.org.landeg.jat.annotation.qualifier.RestToAdapterType;
import uk.org.landeg.jat.api.model.TodoResponseModel;
import uk.org.landeg.jat.objects.TodoInternal;

@Component
@RestToAdapterType(TodoResponseModel.class)
public class TodoResponseAdapter implements ToRestAdapter<TodoResponseModel, TodoInternal>{
	@Override
	public TodoResponseModel toModel(TodoInternal internal) {
		final TodoResponseModel model = new TodoResponseModel();
		BeanUtils.copyProperties(internal, model);
		return model;
	}
}
