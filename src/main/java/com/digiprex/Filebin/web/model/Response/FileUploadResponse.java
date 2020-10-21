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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileUploadResponse implements Serializable {
  private static final long serialVersionUID = 3278782492308852475L;

  private String fileName;
  private String downloadUrl;
}
