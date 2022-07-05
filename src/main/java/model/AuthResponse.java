package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AuthResponse {
  private String expiredAt;
  private String login;
  private String refreshToken;
  private String token;

  @JsonCreator
  public AuthResponse(@JsonProperty("expiredAt")
  String expiredAt, @JsonProperty("login")
  String login, @JsonProperty("refreshToken")
  String refreshToken, @JsonProperty("token") String token) {
    this.expiredAt = expiredAt;
    this.login = login;
    this.refreshToken = refreshToken;
    this.token = token;
  }
}