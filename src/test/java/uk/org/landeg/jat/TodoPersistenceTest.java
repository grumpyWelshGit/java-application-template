package uk.org.landeg.jat;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import uk.org.landeg.jat.api.model.TodoRequestModel;
import uk.org.landeg.jat.api.model.TodoResponseModel;
import uk.org.landeg.jat.jpa.repo.TodoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class TodoPersistenceTest {
	private static RestTemplate restTemplate;

	@LocalServerPort
	private Integer port;

	private String apiEndpoint = null;

	@Autowired
	private TodoRepository todoRepo;

	@BeforeClass
	public static void beforeClass() {
		restTemplate = new RestTemplate();
	}

	@After
	public void teardown() {
		todoRepo.deleteAll();
	}

	@Test
	public void assertRetrieveAll() {
		final String id1 = createTodo("MESSAGE1", HttpStatus.CREATED).getBody().getId();
		final String id2 = createTodo("MESSAGE2", HttpStatus.CREATED).getBody().getId();
		restTemplate.getForEntity(getApiEndpoint(), List.class);
		ParameterizedTypeReference<List<TodoResponseModel>> responseType = new ParameterizedTypeReference<List<TodoResponseModel>>() {};
		final List<String> ids = restTemplate.exchange(getApiEndpoint(), HttpMethod.GET, HttpEntity.EMPTY, responseType)
			.getBody()
			.stream()
			.map(todo -> todo.getId())
			.collect(Collectors.toList());
		Assert.assertEquals(2, ids.size());
		Assert.assertTrue(ids.contains(id1));
		Assert.assertTrue(ids.contains(id2));
	}

	@Test
	public void assertSaved() {
		final String message = UUID.randomUUID().toString();
		ResponseEntity<TodoResponseModel> response = createTodo(message, HttpStatus.CREATED);
		Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
		Assert.assertNotNull(response.getBody().getMessage());
		Assert.assertNotNull(response.getBody().getId());
		Assert.assertEquals(message, response.getBody().getMessage());
	}

	@Test
	public void assertRetrieve() {
		final String message = UUID.randomUUID().toString();
		ResponseEntity<TodoResponseModel> response = createTodo(message, HttpStatus.CREATED);
		final String id = response.getBody().getId();
		final TodoResponseModel retrievedMessage = getTodo(id, HttpStatus.OK);
		Assert.assertEquals(message, retrievedMessage.getMessage());
		Assert.assertEquals(id, retrievedMessage.getId());
	}

	@Test
	public void assertUpdate() {
		final String message = UUID.randomUUID().toString();
		ResponseEntity<TodoResponseModel> response = createTodo(message, HttpStatus.CREATED);
		final String id = response.getBody().getId();
		final TodoRequestModel updateModel = new TodoRequestModel();
		updateModel.setMessage("IVEBEENUPDATED!!!");
		updateModel.setId(id);
		final HttpEntity<TodoRequestModel> updateRequest = new HttpEntity<TodoRequestModel>(updateModel);
		final String endpoint = String.format("%s/%s", getApiEndpoint(), id);
		final HttpEntity<TodoResponseModel> updateResponse = 
				restTemplate.exchange(endpoint, HttpMethod.PUT, updateRequest, TodoResponseModel.class);
		Assert.assertEquals(id, updateResponse.getBody().getId());
		Assert.assertEquals(updateModel.getMessage(), updateResponse.getBody().getMessage());
	}

	@Test
	public void assertDelete() {
		final String message = UUID.randomUUID().toString();
		ResponseEntity<TodoResponseModel> response = createTodo(message, HttpStatus.CREATED);
		final String id = response.getBody().getId();
		final String endpoint = String.format("%s/%s", getApiEndpoint(), id);
		restTemplate.delete(endpoint);
		getTodo(id, HttpStatus.NOT_FOUND);
	}

	private TodoResponseModel getTodo(final String id, final HttpStatus expectedStatus) {
		final String endpoint = String.format("%s/%s", getApiEndpoint(), id);
		ResponseEntity<TodoResponseModel> response = null;
		try {
			response = restTemplate.getForEntity(endpoint, TodoResponseModel.class);
		} catch (HttpClientErrorException e) {
			Assert.assertEquals(expectedStatus, e.getStatusCode());
			return null;
		}
		return response.getBody();
	}

	private ResponseEntity<TodoResponseModel> createTodo(final String message, final HttpStatus expectedStatus) {
		final String endpoint = getApiEndpoint();
		final TodoRequestModel request = new TodoRequestModel();
		request.setMessage(message);
		ResponseEntity<TodoResponseModel> response = restTemplate.postForEntity(endpoint, request, TodoResponseModel.class);
		Assert.assertEquals(expectedStatus, response.getStatusCode());
		return response;
	}

	private String getApiEndpoint() {
		if (apiEndpoint == null) {
			apiEndpoint = String.format("http://localhost:%d/api/v1/todo", port);
		}
		return apiEndpoint;
	}
}