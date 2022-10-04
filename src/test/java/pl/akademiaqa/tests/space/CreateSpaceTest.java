package pl.akademiaqa.tests.space;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import pl.akademiaqa.requests.space.CreateSpaceRequest;
import pl.akademiaqa.requests.space.DeleteSpaceRequest;

class CreateSpaceTest {

    private static final String SPACE_NAME = "mySpaceFromJava";

    @Test
    void createSpaceTest() {

        JSONObject space = new JSONObject();
        space.put("name", SPACE_NAME);

        final var response = CreateSpaceRequest.createSpace(space);
        JsonPath json = response.jsonPath();

        final var spaceId = json.getString("id");

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(json.getString("name")).isEqualTo("mySpaceFromJava");

        final var deleteSpace = DeleteSpaceRequest.deleteSpaceRequest(spaceId);
        Assertions.assertThat(deleteSpace.statusCode()).isEqualTo(200);
    }

}
