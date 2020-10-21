package com.digiprex.Filebin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.digiprex.Filebin.web.model.Response.BaseResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author himanshugupta - created on 21/10/20
 */
@RestControllerAdvice
@Slf4j
public class ErrorController {

  @Value("${spring.servlet.multipart.max-file-size}")
  private String maxFileSize;

  @ExceptionHandler(MaxUploadSizeExceededException.class)
  public BaseResponse handleMaxSizeException(MaxUploadSizeExceededException e) {
    String errorMessage = "File is larger than " + maxFileSize;
    log.error(errorMessage, e);
    return new BaseResponse(false, errorMessage, null);
  }
}
