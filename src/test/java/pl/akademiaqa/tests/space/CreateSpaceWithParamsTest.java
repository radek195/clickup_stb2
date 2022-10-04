package pl.akademiaqa.tests.space;

import io.restassured.path.json.JsonPath;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.akademiaqa.requests.space.CreateSpaceRequest;
import pl.akademiaqa.requests.space.DeleteSpaceRequest;

import java.util.stream.Stream;


class CreateSpaceWithParamsTest {

    @ParameterizedTest(name = "Create space with space name: {0}")
    @DisplayName("Create valid space name")
    @MethodSource("createSpaceData")
    void createSpaceTest(String spaceName) {

        JSONObject space = new JSONObject();
        space.put("name", spaceName);

        final var response = CreateSpaceRequest.createSpace(space);
        JsonPath json = response.jsonPath();

        final var spaceId = json.getString("id");

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(json.getString("name")).isEqualTo(spaceName);

        final var deleteSpace = DeleteSpaceRequest.deleteSpaceRequest(spaceId);
        Assertions.assertThat(deleteSpace.statusCode()).isEqualTo(200);
    }

    private static Stream<Arguments> createSpaceData() {
        return Stream.of(
                Arguments.of("TESTtest"),
                Arguments.of("@"),
                Arguments.of("#"),
                Arguments.of("$"),
                Arguments.of("%"),
                Arguments.of("*")
        );
    }
}
