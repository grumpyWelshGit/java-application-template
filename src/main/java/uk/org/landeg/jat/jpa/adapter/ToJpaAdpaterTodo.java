package uk.org.landeg.jat.jpa.adapter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import uk.org.landeg.jat.annotation.qualifier.JpaToAdapterType;
import uk.org.landeg.jat.jpa.domain.TodoJpa;
import uk.org.landeg.jat.objects.TodoInternal;

@Component
@JpaToAdapterType(TodoJpa.class)
public class ToJpaAdpaterTodo implements ToJpaAdatper<TodoJpa, TodoInternal>{

	@Override
	public TodoJpa toJpa(TodoInternal business, TodoJpa jpa) {
		if (jpa == null) {
			jpa = new TodoJpa();
		}
		BeanUtils.copyProperties(business, jpa);
		return jpa;
	}

}
