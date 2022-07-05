
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.util.Properties;

public class BaseTest {

  public RequestSpecification REQUEST;
  Properties props = new Properties();

  public BaseTest() {
    try {
      props.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    RestAssured.baseURI = props.getProperty("api.url");
    REQUEST = RestAssured.given().contentType(ContentType.JSON).urlEncodingEnabled(false);
  }
}
