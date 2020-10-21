package com.digiprex.Filebin.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.digiprex.Filebin.entity.FileInfo;

/**
 * @author himanshugupta - created on 21/10/20
 */
public interface FileInfoRepository extends CrudRepository<FileInfo, String> {

  @Modifying
  @Query("update FileInfo f set f.downloaded = true where f.id = :id and f.downloaded = false")
  int updateDownloadedStatus(@Param("id") String id);
}
