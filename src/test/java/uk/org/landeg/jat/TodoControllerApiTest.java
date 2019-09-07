package uk.org.landeg.jat;

import java.util.UUID;

import org.junit.Test;

import uk.org.landeg.jat.api.TodoApiController;
import uk.org.landeg.jat.api.error.BadRequestException;
import uk.org.landeg.jat.api.model.TodoRequestModel;

public class TodoControllerApiTest {
	private TodoApiController controller = new TodoApiController();

	@Test(expected = BadRequestException.class)
	public void assertBadRequestOnInconsistentPutIds() {
		final TodoRequestModel model = new TodoRequestModel();
		model.setId(UUID.randomUUID().toString());
		final String pathId = UUID.randomUUID().toString();
		controller.updateTodo(pathId, model);
	}
}
