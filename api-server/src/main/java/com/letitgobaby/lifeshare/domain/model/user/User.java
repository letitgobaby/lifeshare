package com.letitgobaby.lifeshare.domain.model.user;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.letitgobaby.lifeshare.domain.common.model.DateBaseEntity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "user")
@Where(clause = "isDeleted = false")
@SQLDelete(sql = "UPDATE user SET isDeleted = true WHERE id = ?")
public class User extends DateBaseEntity implements Serializable {

  private static final long serialVersionUID = -1153931912966528994L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, updatable = false)
  private Long id;

  @Column(name = "user_name", length = 25)
  private String userName;

  @Column(name = "nick_name", length = 100, nullable = false)
  private String nickName;

  @Column(name = "password", length = 200, nullable = false)
  private String password;

  @Column(name = "phone_number", length = 14, nullable = false)
  private String phoneNumber;

  @Column(name = "email", length = 100, nullable = false)
  private String email;

  @Column(name = "thumbnail_image", nullable = true)
  private String thumbnail;

  @Column(name = "last_login")
  private LocalDateTime lastLogin;

  @Column(name = "is_deleted", nullable = false)
  private Boolean isDeleted = Boolean.FALSE;

  public User regist(String userName, String nickName, String password, String phoneNumber, String email) {
    User user = new User();
    user.userName = userName;
    user.nickName = nickName;
    user.password = encodePassword(password);
    user.phoneNumber = phoneNumber;
    user.email = email;
    user.lastLogin = LocalDateTime.now();
    return user;
  }

  private String encodePassword(String password) {
    return password;
  }

  public Long getId() { return this.id; }

  public String getUserName() { return this.userName; }

  // public String getPassword() { return this.password; }

  public String getPhoneNumber() { return this.phoneNumber; }

  public String getEmail() { return this.email; }

  public String getThumbnail() { return this.thumbnail; }

  public LocalDateTime getLastLogin() { return this.lastLogin; }

}
