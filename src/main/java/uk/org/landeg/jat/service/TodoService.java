package uk.org.landeg.jat.service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.org.landeg.jat.annotation.qualifier.FromRestAdapterType;
import uk.org.landeg.jat.annotation.qualifier.JpaFromAdapterType;
import uk.org.landeg.jat.annotation.qualifier.JpaToAdapterType;
import uk.org.landeg.jat.annotation.qualifier.ToRestAdapterType;
import uk.org.landeg.jat.api.error.NotFoundClientException;
import uk.org.landeg.jat.api.model.TodoRequestModel;
import uk.org.landeg.jat.api.model.TodoResponseModel;
import uk.org.landeg.jat.jpa.domain.TodoJpa;
import uk.org.landeg.jat.jpa.repo.TodoRepository;
import uk.org.landeg.jat.objects.TodoInternal;
import uk.org.landeg.jat.transformer.GenericTransformer;

@Service
public class TodoService {
	@Autowired
	@JpaToAdapterType(TodoJpa.class)
	private GenericTransformer<TodoInternal, TodoJpa> toJpaAdapter;

	@Autowired
	@JpaFromAdapterType(TodoJpa.class)
	private GenericTransformer<TodoJpa, TodoInternal> fromJpaAdapter;

	@Autowired
	@FromRestAdapterType(TodoRequestModel.class)
	private GenericTransformer<TodoRequestModel, TodoInternal> fromRestAdapter;

	@Autowired
	@ToRestAdapterType(TodoResponseModel.class)
	private GenericTransformer<TodoInternal, TodoResponseModel> toRestAdapter;

	@Autowired
	private TodoRepository todoRepository;

	public Iterable<TodoResponseModel> getTodos() {
		return StreamSupport.stream(todoRepository.findAll().spliterator(), false)
			.map(jpa -> fromJpaAdapter.apply(jpa, null))
			.map(internal -> toRestAdapter.apply(internal))
			.collect(Collectors.toList());
	}

	public TodoResponseModel findTodo(String id) {
		return toRestAdapter.apply(fromJpaAdapter.apply(retrieveTodo(id), null));
	}

	public TodoResponseModel saveTodo(final TodoRequestModel model) {
		TodoInternal todo = fromRestAdapter.apply(model);
		TodoJpa jpa = toJpaAdapter.apply(todo, null);

		jpa = todoRepository.save(jpa);

		todo = fromJpaAdapter.apply(jpa, todo);
		return toRestAdapter.apply(todo); 
	}

	public TodoResponseModel updateTodo(final String id, final TodoRequestModel model) {
		final TodoJpa jpa = retrieveTodo(id);
		final TodoInternal update = fromRestAdapter.apply(model);
		
		toJpaAdapter.apply(update, jpa);

		todoRepository.save(jpa);

		final TodoInternal updated = fromJpaAdapter.apply(jpa, null);
		return toRestAdapter.apply(updated);
	}
	
	public void deleteTodo(String id) {
		retrieveTodo(id);
		todoRepository.deleteById(id);
	}

	private TodoJpa retrieveTodo(final String id) {
		return todoRepository.findById(id)
				.orElseThrow(() -> new NotFoundClientException("entity does not exist"));
	}

}
