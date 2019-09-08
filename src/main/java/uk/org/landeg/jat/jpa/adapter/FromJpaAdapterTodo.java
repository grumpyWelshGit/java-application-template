package uk.org.landeg.jat.jpa.adapter;

import org.springframework.stereotype.Component;

import uk.org.landeg.jat.annotation.qualifier.JpaFromAdapterType;
import uk.org.landeg.jat.jpa.domain.TodoJpa;
import uk.org.landeg.jat.objects.TodoInternal;
import uk.org.landeg.jat.transformer.AbstractGenericTransformer;

@Component
@JpaFromAdapterType(TodoJpa.class)
public class FromJpaAdapterTodo extends AbstractGenericTransformer<TodoJpa, TodoInternal> {
	public FromJpaAdapterTodo() {
		super(TodoInternal.class);
	}
}
