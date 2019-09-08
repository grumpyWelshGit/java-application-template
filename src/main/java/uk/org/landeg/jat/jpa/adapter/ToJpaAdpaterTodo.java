package uk.org.landeg.jat.jpa.adapter;

import org.springframework.stereotype.Component;

import uk.org.landeg.jat.annotation.qualifier.JpaToAdapterType;
import uk.org.landeg.jat.jpa.domain.TodoJpa;
import uk.org.landeg.jat.objects.TodoInternal;
import uk.org.landeg.jat.transformer.AbstractGenericTransformer;

@Component
@JpaToAdapterType(TodoJpa.class)
public class ToJpaAdpaterTodo extends AbstractGenericTransformer<TodoInternal, TodoJpa>{

	public ToJpaAdpaterTodo() {
		super(TodoJpa.class);
	}


}
