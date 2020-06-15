package com.dev.admin.admin;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User implements Serializable{
  private static final long serialVersionUID = -2343243243242432341L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;

  @NotEmpty
  @Column(length = 32)
  private String username;
  
  @NotEmpty
  @Column(length = 64)
  private String password;
  
  @NotEmpty
  @Column(length = 128)
  private String name;
  
  private Long phone;

  private boolean valid;
  
  @ElementCollection(fetch = FetchType.EAGER)
  private List<String> roles = new ArrayList<>();
  
  public User() {
  }
  public User(@NotEmpty String username, @NotEmpty String password, @NotEmpty String name, Long phone, boolean valid, List<String> roles) {
    super();
    this.username=username;
    this.password=password;
    this.name=name;
    this.phone=phone;
    this.valid=valid;
    this.roles=roles;
  }
  
  public String getPassword() {
    return this.password;
  }
  
  public String getUsername() {
    return this.username;
  }
  
  public String getName() {
    return this.name;
  }
  public Long getPhone() {
    return this.phone;
  }

  public boolean getValid(){
    return this.valid;
  }
  
  public List<String> getRoles(){
    return this.roles;
  }
  
  public void setPassword(String password){
    this.password=password;
  }
  
  public void setUsername(String username){
    this.username=username;
  }
  
  public void setName(String name){
    this.name=name;
  }
  public void setPhone(Long phone) {
    this.phone=phone;
  }

  public void setValid(boolean valid){
    this.valid=valid;
  }
  
  public void setRoles(List<String> roles){
    this.roles=roles;
  }
}