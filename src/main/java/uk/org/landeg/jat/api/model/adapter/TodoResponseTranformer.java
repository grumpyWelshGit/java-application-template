package uk.org.landeg.jat.api.model.adapter;

import org.springframework.stereotype.Component;

import uk.org.landeg.jat.annotation.qualifier.ToRestAdapterType;
import uk.org.landeg.jat.api.model.TodoResponseModel;
import uk.org.landeg.jat.objects.TodoInternal;
import uk.org.landeg.jat.transformer.AbstractGenericTransformer;

@Component
@ToRestAdapterType(TodoResponseModel.class)
public class TodoResponseTranformer extends AbstractGenericTransformer<TodoInternal, TodoResponseModel> {
	public TodoResponseTranformer() {
		super(TodoResponseModel.class);
	}
}
