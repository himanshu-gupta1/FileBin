package com.digiprex.Filebin.web.model.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author himanshugupta - created on 21/10/20
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDto<T> extends BaseResponse {
  private T content;

  public ResponseDto(boolean success, String errorMessage, String successMessage, T content) {
    super(success, errorMessage, successMessage);
    this.content = content;
  }
}
