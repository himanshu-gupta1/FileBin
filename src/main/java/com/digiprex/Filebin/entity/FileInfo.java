package com.digiprex.Filebin.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author himanshugupta - created on 21/10/20
 */
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileInfo {
  @Id
  private String id;
  private boolean downloaded;
  private String fileName;
}
