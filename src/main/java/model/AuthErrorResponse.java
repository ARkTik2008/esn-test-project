package model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthErrorResponse {
  private Date timestamp;
  private Integer status;
  private String error;
  private String message;
}
