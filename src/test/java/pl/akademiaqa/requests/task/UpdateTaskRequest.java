package pl.akademiaqa.requests.task;

import io.restassured.response.Response;
import org.json.JSONObject;
import pl.akademiaqa.requests.BaseRequest;
import pl.akademiaqa.url.ClickupURL;

import static io.restassured.RestAssured.given;

public class UpdateTaskRequest {

    public static Response updateTask(JSONObject body, String taskId) {
        return given()
                .spec(BaseRequest.requestSpec())
                .body(body.toString())
                .when()
                .put(ClickupURL.getTaskUrl(taskId))
                .then()
                .log().ifError()
                .extract().response();
    }
}
