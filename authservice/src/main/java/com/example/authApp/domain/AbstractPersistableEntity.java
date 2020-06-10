package com.example.authApp.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract  class AbstractPersistableEntity<ID> implements Serializable {

	private static final long serialVersionUID = -761503632186596342L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private ID id;

    @Version
    private Long version;
}
