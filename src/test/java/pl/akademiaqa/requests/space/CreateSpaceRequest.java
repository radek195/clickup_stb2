package pl.akademiaqa.requests.space;

import io.restassured.response.Response;
import org.json.JSONObject;
import pl.akademiaqa.properties.ClickupProperties;
import pl.akademiaqa.requests.BaseRequest;
import pl.akademiaqa.url.ClickupURL;

import static io.restassured.RestAssured.given;

public class CreateSpaceRequest {

    public static Response createSpace(JSONObject space) {
        return given()
                .spec(BaseRequest.requestSpec())
                .body(space.toString())
                .when()
                .post(ClickupURL.getSpacesUrl(ClickupProperties.getTeamId()))
                .then()
                .log().ifError()
                .extract().response();
    }

}
