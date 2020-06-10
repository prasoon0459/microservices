package com.dev.admin.admin;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;

import java.io.Serializable;



@Entity
@Table(name="users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User implements Serializable{

    private static final long serialVersionUID = -2343243243242432341L;
    @Id
    @GeneratedValue
    Long id;
    
    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
    
    @NotEmpty
    private String name;
    
    @NotEmpty
    private String phone;       

    public User() {
    }
    public User(@NotEmpty String username, @NotEmpty String password, @NotEmpty String name, @NotEmpty String phone) {
		super();
		this.username = username;
		this.password = password;
		this.name=name;
		this.phone=phone;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getName() {
		return name;
	}
	public String getPhone() {
		return phone;
	}

}