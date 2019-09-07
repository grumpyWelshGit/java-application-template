package uk.org.landeg.jat;

import java.util.UUID;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import uk.org.landeg.jat.api.model.TodoRequestModel;
import uk.org.landeg.jat.api.model.TodoResponseModel;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class TodoPersistenceTest {
	private static RestTemplate restTemplate;

	@LocalServerPort
	private Integer port;

	private String apiEndpoint = null;

	@BeforeClass
	public static void beforeClass() {
		restTemplate = new RestTemplate();
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