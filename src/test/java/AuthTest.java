import io.restassured.response.Response;
import java.util.stream.Stream;
import model.AuthErrorResponse;
import model.AuthRequest;
import model.AuthResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;


public class AuthTest extends BaseTest {

  static final String LOGIN_PATH = "/login";

  @Test
  public void successfulLoginWithValidCredsTest() {
    String validLogin = "validLogin";
    String validPassword = "validPassword";

    var validCredential = new AuthRequest(validLogin, validPassword);
    Response authResponse = REQUEST.body(validCredential)
        .post(LOGIN_PATH);

    authResponse
        .then()
        .statusCode(HttpStatus.SC_OK);

    var authResponseBody = authResponse.as(AuthResponse.class);

    assertThat(authResponseBody.getLogin()).isEqualTo(validLogin);
    assertThat(authResponseBody.getToken()).isNotNull();
    assertThat(authResponseBody.getExpiredAt()).isNotNull();
    assertThat(authResponseBody.getExpiredAt()).isNotNull();
  }

  @Test
  public void unsuccessfulLoginWithInvalidPassTest() {
    String validLogin = "validLogin";
    String invalidPassword = "invalidPassword";

    var invalidCredential = new AuthRequest(validLogin, invalidPassword);
    Response authResponse = REQUEST.body(invalidCredential)
        .post(LOGIN_PATH);

    authResponse
        .then()
        .statusCode(HttpStatus.SC_UNAUTHORIZED);

    var authResponseBody = authResponse.as(AuthErrorResponse.class);

    assertThat(authResponseBody.getStatus()).isEqualTo(HttpStatus.SC_UNAUTHORIZED);
    assertThat(authResponseBody.getMessage()).isNotNull();
    assertThat(authResponseBody.getError()).isNotNull();
    assertThat(authResponseBody.getTimestamp()).isNotNull();
  }

  @ParameterizedTest(name = "Should receive bad request with blank creds: login - {0}, pass - {1} ")
  @MethodSource("provideCredsForIsBlank")
  public void badRequestErrorWithBlankCredsTest(String login, String pass) {
    var invalidCredential = new AuthRequest(login, pass);
    Response authResponse = REQUEST.body(invalidCredential)
        .post(LOGIN_PATH);

    authResponse
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST);

    var authResponseBody = authResponse.as(AuthErrorResponse.class);

    assertThat(authResponseBody.getStatus()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
    assertThat(authResponseBody.getMessage()).isNotNull();
    assertThat(authResponseBody.getError()).isNotNull();
    assertThat(authResponseBody.getTimestamp()).isNotNull();
  }

  private static Stream<Arguments> provideCredsForIsBlank() {
    return Stream.of(
        Arguments.of(null, null),
        Arguments.of("", null),
        Arguments.of("  ", null),
        Arguments.of(null, "  "),
        Arguments.of(null, ""),
        Arguments.of("validLogin", ""),
        Arguments.of("validLogin", null)
    );
  }

}
