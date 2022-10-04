package pl.akademiaqa.requests.space;

import io.restassured.response.Response;
import pl.akademiaqa.requests.BaseRequest;
import pl.akademiaqa.url.ClickupURL;

import static io.restassured.RestAssured.given;

public class DeleteSpaceRequest {

    public static Response deleteSpaceRequest(String spaceId) {
        return given()
                .spec(BaseRequest.requestSpec())
                .when()
                .delete(ClickupURL.getSpaceUrl(spaceId))
                .then()
                .log().ifError()
                .extract().response();
    }
}
