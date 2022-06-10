package com.letitgobaby.lifeshare.domain.common.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
public class DateBaseEntity {
  
  @CreationTimestamp
  @Column(name = "create_date")
  private LocalDateTime createDate;

  @UpdateTimestamp
  @Column(name = "update_date")
  private LocalDateTime updateDate;

  public LocalDateTime getCreateDate() { return this.createDate; }
  public LocalDateTime getUpdateDate() { return this.updateDate; }
  
}
