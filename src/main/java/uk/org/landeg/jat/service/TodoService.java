package uk.org.landeg.jat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.datatype.jdk8.StreamSerializer;

import uk.org.landeg.jat.annotation.qualifier.JpaFromAdapterType;
import uk.org.landeg.jat.annotation.qualifier.JpaToAdapterType;
import uk.org.landeg.jat.annotation.qualifier.RestFromAdapterType;
import uk.org.landeg.jat.annotation.qualifier.RestToAdapterType;
import uk.org.landeg.jat.api.error.NotFoundClientException;
import uk.org.landeg.jat.api.model.TodoRequestModel;
import uk.org.landeg.jat.api.model.TodoResponseModel;
import uk.org.landeg.jat.api.model.adapter.FromRestAdapter;
import uk.org.landeg.jat.api.model.adapter.ToRestAdapter;
import uk.org.landeg.jat.jpa.adapter.FromJpaAdapter;
import uk.org.landeg.jat.jpa.adapter.ToJpaAdatper;
import uk.org.landeg.jat.jpa.domain.TodoJpa;
import uk.org.landeg.jat.jpa.repo.TodoRepository;
import uk.org.landeg.jat.objects.TodoInternal;

@Service
public class TodoService {
	@Autowired
	@JpaToAdapterType(TodoJpa.class)
	private ToJpaAdatper<TodoJpa, TodoInternal> toJpaAdapter;

	@Autowired
	@JpaFromAdapterType(TodoJpa.class)
	private FromJpaAdapter<TodoJpa, TodoInternal> fromJpaAdapter;

	@Autowired
	@RestFromAdapterType(TodoRequestModel.class)
	private FromRestAdapter<TodoRequestModel, TodoInternal> fromRestAdapter;

	@Autowired
	@RestToAdapterType(TodoResponseModel.class)
	private ToRestAdapter<TodoResponseModel, TodoInternal> toRestAdapter;

	@Autowired
	private TodoRepository todoRepository;

	public Iterable<TodoResponseModel> getTodos() {
		return StreamSupport.stream(todoRepository.findAll().spliterator(), false)
			.map(jpa -> fromJpaAdapter.fromJpa(jpa, null))
			.map(internal -> toRestAdapter.toModel(internal))
			.collect(Collectors.toList());
	}

	public TodoResponseModel findTodo(String id) {
		return toRestAdapter.toModel(fromJpaAdapter.fromJpa(retrieveTodo(id), null));
	}

	public TodoResponseModel saveTodo(final TodoRequestModel model) {
		TodoInternal todo = fromRestAdapter.fromRest(model);
		TodoJpa jpa = toJpaAdapter.toJpa(todo, null);

		jpa = todoRepository.save(jpa);

		todo = fromJpaAdapter.fromJpa(jpa, todo);
		return toRestAdapter.toModel(todo); 
	}

	public TodoResponseModel updateTodo(final String id, final TodoRequestModel model) {
		final TodoJpa jpa = retrieveTodo(id);
		final TodoInternal update = fromRestAdapter.fromRest(model);
		
		toJpaAdapter.toJpa(update, jpa);

		todoRepository.save(jpa);

		final TodoInternal updated = fromJpaAdapter.fromJpa(jpa, null);
		return toRestAdapter.toModel(updated);
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
