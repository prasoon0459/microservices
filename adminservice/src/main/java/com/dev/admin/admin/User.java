package com.dev.admin.admin;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name="users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
@SecondaryTable(name="user_roles",pkJoinColumns = @PrimaryKeyJoinColumn(name="user_id"))
public class User implements Serializable{
  
  private static final long serialVersionUID = -2343243243242432341L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  
  @NotEmpty
  private String username;
  
  @NotEmpty
  private String password;
  
  @NotEmpty
  private String name;
  
  @NotEmpty
  private String phone;

  @NotEmpty
  private boolean valid;
  
  @Column(name="roles",table="user_roles")
  @ElementCollection(fetch = FetchType.EAGER)
  private List<String> roles = new ArrayList<>();
  
  public User() {
  }
  /*public User(@NotEmpty String username, @NotEmpty String password, @NotEmpty String name, @NotEmpty String phone,  List<String> roles) {
    super();
    this.username = username;
    this.password = password;
    this.name=name;
    this.phone=phone;
    this.roles=roles;
  }*/

  public Long getId(){
    return this.id;
  }
  
  public String getPassword(){
    return this.password;
  }
  
  public String getUsername(){
    return this.username;
  }
  
  public String getName(){
    return this.name;
  }
  public String getPhone(){
    return this.phone;
  }

  public boolean getValid(){
    return this.valid;
  }

  @OneToMany(mappedBy = "user_roles")
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
  public void setPhone(String phone){
    this.phone=phone;
  }
  
  public void setRoles(List<String> roles){
    this.roles=roles;
  }

  public void setValid(boolean valid){
    this.valid=valid;
  }
}