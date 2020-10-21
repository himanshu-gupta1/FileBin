package com.digiprex.Filebin.controller.util;

import com.digiprex.Filebin.web.model.Response.FileUploadResponse;

/**
 * @author himanshugupta - created on 21/10/20
 */
public class ConverterUtil {

  public static FileUploadResponse toFileUploadResponse(String fileName, String url) {
    return FileUploadResponse.builder()
        .fileName(fileName)
        .downloadUrl(url)
        .build();
  }
}
