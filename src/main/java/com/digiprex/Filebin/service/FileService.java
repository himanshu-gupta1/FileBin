package com.digiprex.Filebin.service;

import java.io.IOException;

import org.springframework.data.util.Pair;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author himanshugupta - created on 21/10/20
 */
public interface FileService {

  /**
   * Upload File And generate a url for the same
   *
   * @param file
   * @return
   */
  String uploadFile(MultipartFile file) throws IOException;

  /**
   * Download file using file name
   *
   * @param fileName
   * @return
   */
  Pair<String, byte[]> downloadFile(String fileName) throws IOException;
}
