package uk.org.landeg.jat.jpa.adapter;

import org.springframework.stereotype.Component;

import uk.org.landeg.jat.annotation.qualifier.JpaFromAdapterType;
import uk.org.landeg.jat.jpa.domain.TodoJpa;
import uk.org.landeg.jat.objects.TodoInternal;

@Component
@JpaFromAdapterType(TodoJpa.class)
public class FromJpaAdapterTodo extends FromJpaAdapterAbstract<TodoJpa, TodoInternal>implements FromJpaAdapter<TodoJpa, TodoInternal>{
	public FromJpaAdapterTodo() {
		super(TodoJpa.class, TodoInternal.class);
	}
}
