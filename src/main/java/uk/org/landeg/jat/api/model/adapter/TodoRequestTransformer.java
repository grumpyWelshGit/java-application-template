package uk.org.landeg.jat.api.model.adapter;

import org.springframework.stereotype.Component;

import uk.org.landeg.jat.annotation.qualifier.FromRestAdapterType;
import uk.org.landeg.jat.api.model.TodoRequestModel;
import uk.org.landeg.jat.objects.TodoInternal;
import uk.org.landeg.jat.transformer.AbstractGenericTransformer;

@Component
@FromRestAdapterType(TodoRequestModel.class)
public class TodoRequestTransformer extends AbstractGenericTransformer<TodoRequestModel, TodoInternal> {

	public TodoRequestTransformer() {
		super(TodoInternal.class);
	}

}
