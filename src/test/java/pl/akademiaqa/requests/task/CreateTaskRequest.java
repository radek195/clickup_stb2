package pl.akademiaqa.requests.task;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.json.JSONObject;
import pl.akademiaqa.DTO.request.CreateTaskRequestDTO;
import pl.akademiaqa.DTO.response.CreateTaskResponseDTO;
import pl.akademiaqa.requests.BaseRequest;
import pl.akademiaqa.url.ClickupURL;

import static io.restassured.RestAssured.given;

public class CreateTaskRequest {

    public static Response createTask(JSONObject task, String listId) {
        return given()
                .spec(BaseRequest.requestSpec())
                .body(task.toString())
                .when()
                .post(ClickupURL.getTasksUrl(listId))
                .then()
                .log().ifError()
                .extract().response();
    }

    public static CreateTaskResponseDTO createTask(CreateTaskRequestDTO taskDTO, String listId) {
        return given()
                .spec(BaseRequest.requestSpec())
                .body(taskDTO)
                .when()
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .post(ClickupURL.getTasksUrl(listId))
                .then()
                .statusCode(200)
                .log().ifError()
                .extract().response()
                .as(CreateTaskResponseDTO.class);
    }
}
