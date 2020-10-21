package com.digiprex.Filebin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.digiprex.Filebin.controller.util.ConverterUtil;
import com.digiprex.Filebin.model.Constants;
import com.digiprex.Filebin.model.FileApiPath;
import com.digiprex.Filebin.service.FileService;
import com.digiprex.Filebin.web.model.Response.FileUploadResponse;
import com.digiprex.Filebin.web.model.Response.ResponseDto;
import lombok.extern.slf4j.Slf4j;

/**
 * @author himanshugupta - created on 21/10/20
 */
@RestController
@Slf4j
public class FileController {

  @Autowired
  private FileService fileService;

  @PostMapping(value = FileApiPath.UPLOAD)
  public ResponseDto<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile request) {
    String fileName = request.getOriginalFilename();
    log.info("Upload triggered for file : {}Upload triggered for file : {}", fileName);
    try {
      String url = fileService.uploadFile(request);
      String message = String.format(Constants.FILE_UPLOAD_SUCCESS_MESSAGE, fileName);
      log.info(message);
      return new ResponseDto<>(true, null, message, ConverterUtil.toFileUploadResponse(fileName, url));
    } catch (RuntimeException rx) {
      log.error("Error in uploading file : {}, {}", fileName, rx);
      return new ResponseDto<>(false, rx.getMessage(), null, null);
    } catch (Exception e) {
      log.error("Error in uploading file : {}, {}", fileName, e);
      return new ResponseDto<>(false, "Unspecifed", null, null);
    }
  }

  @GetMapping(value = FileApiPath.DOWNLOAD)
  public ResponseEntity<Object> downloadFile(@RequestParam("fileName") String fileName) {
    log.info("Download triggered for file : {}", fileName);
    try {
      Pair<String, byte[]> response = fileService.downloadFile(fileName);
      return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + response.getFirst())
          .body(response.getSecond());
    } catch (RuntimeException rx) {
      log.error("Error in downloading file : {}", fileName, rx);
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(rx.getMessage());
    } catch (Exception e) {
      log.error("Error in downloading file : {}", fileName, e);
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Unspecifed");
    }
  }
}
