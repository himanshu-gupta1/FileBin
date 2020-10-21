package com.digiprex.Filebin.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.digiprex.Filebin.entity.FileInfo;
import com.digiprex.Filebin.model.FileApiPath;
import com.digiprex.Filebin.model.Constants;
import com.digiprex.Filebin.repository.FileInfoRepository;


/**
 * @author himanshugupta - created on 21/10/20
 */
@Service
public class FileServiceImpl implements FileService {

  @Value("${server.port}")
  private String port;

  private static final String BASE_DIR = new File("src/main/resources/files").getAbsolutePath();

  @Autowired
  private FileInfoRepository fileInfoRepository;

  @Override
  public String uploadFile(MultipartFile request) throws IOException {
    String id = UUID.randomUUID().toString();
    String fileName = id + Constants.DOT + FilenameUtils.getExtension(request.getOriginalFilename());
    File file = new File(BASE_DIR + Constants.PATH_SEPERATOR + fileName);
    request.transferTo(file);
    try {
      insertFileInfoInDbForUploadedFile(id, request);
    }catch (Exception e){
      file.delete();
      throw new RuntimeException("Error in persisting file info");
    }
    return InetAddress.getLocalHost().getHostAddress() + Constants.COLON + port + FileApiPath.DOWNLOAD + "?fileName="
        + fileName;
  }

  @Override
  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public Pair<String, byte[]> downloadFile(String fileName) throws IOException {
    File excelFile = new File(BASE_DIR + Constants.PATH_SEPERATOR + fileName);
    FileInfo fileInfo = validateAndUpdateFileDownloadStatus(fileName);
    try (FileInputStream fileInputStream = new FileInputStream(excelFile)) {
      return Pair.of(fileInfo.getFileName(), IOUtils.toByteArray(fileInputStream));
    }
  }

  private FileInfo validateAndUpdateFileDownloadStatus(String fileName) {
    String id = FilenameUtils.getBaseName(fileName);
    int result = fileInfoRepository.updateDownloadedStatus(id);
    if (result == 0) {
      throw new RuntimeException("File is not present or Already Downloaded");
    }
    return fileInfoRepository.findById(id).get();
  }

  private void insertFileInfoInDbForUploadedFile(String id, MultipartFile request) {
    FileInfo fileInfo = FileInfo.builder()
        .fileName(request.getOriginalFilename())
        .downloaded(false)
        .id(id)
        .build();
    fileInfoRepository.save(fileInfo);
  }
}
