package com.digiprex.Filebin.web.model.Response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author himanshugupta - created on 21/10/20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse implements Serializable {
  private static final long serialVersionUID = -5102460609622297146L;

  private boolean success;
  private String errorMessage;
  String successMessage;
}
