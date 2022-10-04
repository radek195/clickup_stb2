package pl.akademiaqa.requests.list;

import io.restassured.response.Response;
import org.json.JSONObject;
import pl.akademiaqa.requests.BaseRequest;
import pl.akademiaqa.url.ClickupURL;

import static io.restassured.RestAssured.given;

public class CreateListRequest {

    public static Response createList(JSONObject list, String spaceId) {
        return given()
                .spec(BaseRequest.requestSpec())
                .body(list.toString())
                .when()
                .post(ClickupURL.getListsUrl(spaceId))
                .then()
                .log().ifError()
                .extract().response();
    }
}
