package pl.akademiaqa.tests.e2e;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.akademiaqa.DTO.request.CreateTaskRequestDTO;
import pl.akademiaqa.requests.list.CreateListRequest;
import pl.akademiaqa.requests.space.CreateSpaceRequest;
import pl.akademiaqa.requests.space.DeleteSpaceRequest;
import pl.akademiaqa.requests.task.CreateTaskRequest;
import pl.akademiaqa.requests.task.UpdateTaskRequest;

class UpdateTaskE2ETest {

    private static final Logger log = LoggerFactory.getLogger(UpdateTaskE2ETest.class);
    private String spaceName = "SPACE E2E";
    private String listName = "Tasks to complete";
    private String taskName = "Create tests";
    private String changedTaskName = "Create tests - changed!";
    private String description = "Default description";
    private String changedDescription = "Description - changed!";
    private String spaceId;
    private String listId;
    private String taskId;

    @Test
    void updateTaskE2ETest() {
        spaceId = createSpaceStep();
        log.info("Space has been created with id: {}", spaceId);

        listId = createListStep();
        log.info("List has been created with id: {}", listId);

        taskId = createTaskStep();
        log.info("Task has been created with id: {}", taskId);

        updateTaskStep();
        closeTaskStep();
        deleteSpaceStep();
    }

    private String createSpaceStep() {
        JSONObject json = new JSONObject();
        json.put("name", spaceName);
        final var space = CreateSpaceRequest.createSpace(json);
        Assertions.assertThat(space.getStatusCode()).isEqualTo(200);

        JsonPath jsonData = space.jsonPath();
        Assertions.assertThat(jsonData.getString("name")).isEqualTo(spaceName);

        return jsonData.getString("id");
    }

    private String createListStep() {
        JSONObject json = new JSONObject();
        json.put("name", listName);

        final var list = CreateListRequest.createList(json, spaceId);
        Assertions.assertThat(list.getStatusCode()).isEqualTo(200);

        JsonPath jsonData = list.jsonPath();
        Assertions.assertThat(jsonData.getString("name")).isEqualTo(listName);

        return jsonData.getString("id");
    }

    private String createTaskStep() {
        CreateTaskRequestDTO taskDTO = new CreateTaskRequestDTO();
        taskDTO.setName(taskName);
        taskDTO.setDescription(description);
        taskDTO.setStatus("to do");
        taskDTO.setArchived(false);

        final var task = CreateTaskRequest.createTask(taskDTO, listId);

        Assertions.assertThat(task.getName()).isEqualTo(taskName);
        Assertions.assertThat(task.getDescription()).isEqualTo(description);
        Assertions.assertThat(task.getArchived()).isFalse();


        return task.getId();
    }

    private void updateTaskStep() {
        JSONObject task = new JSONObject();
        task.put("name", changedTaskName);
        task.put("description", changedDescription);

        Response response = UpdateTaskRequest.updateTask(task, taskId);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);

        JsonPath jsonData = response.jsonPath();
        Assertions.assertThat(jsonData.getString("name")).isEqualTo(changedTaskName);
        Assertions.assertThat(jsonData.getString("description")).isEqualTo(changedDescription);
    }

    private void closeTaskStep() {
        JSONObject closeTask = new JSONObject();
        closeTask.put("status", "complete");

        Response response = UpdateTaskRequest.updateTask(closeTask, taskId);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);

        JsonPath jsonData = response.jsonPath();
        Assertions.assertThat(jsonData.getString("name")).isEqualTo(changedTaskName);
        Assertions.assertThat(jsonData.getString("description")).isEqualTo(changedDescription);
        Assertions.assertThat(jsonData.getString("status.status")).isEqualTo("complete");
    }

    private void deleteSpaceStep() {
        Response response = DeleteSpaceRequest.deleteSpaceRequest(spaceId);

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
    }
}
