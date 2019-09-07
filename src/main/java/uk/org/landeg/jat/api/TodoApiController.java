package uk.org.landeg.jat.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.org.landeg.jat.api.error.BadRequestException;
import uk.org.landeg.jat.api.model.TodoRequestModel;
import uk.org.landeg.jat.api.model.TodoResponseModel;
import uk.org.landeg.jat.service.TodoService;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoApiController {
	@Autowired
	private TodoService testService;

	@GetMapping()
	public Iterable<TodoResponseModel> getTests() {
		return testService.getTodos();
	}

	@GetMapping("/{id}")
	public TodoResponseModel getTodo(@PathVariable ("id") final String id) {
		return testService.findTodo(id);
	}

	@PostMapping
	public ResponseEntity<TodoResponseModel> createTodo(@RequestBody final TodoRequestModel model) {
		return ResponseEntity.status(HttpStatus.CREATED).body(testService.saveTodo(model));
	}

	@PutMapping("/{id}")
	public ResponseEntity<TodoResponseModel> updateTodo(
			@PathVariable("id") final String id,
			@RequestBody final TodoRequestModel model) {
		if (!id.equals(model.getId())) {
			throw new BadRequestException("model and path id are inconsistent");
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(testService.updateTodo(id, model));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable("id") final String id) {
		testService.deleteTodo(id);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}
}
