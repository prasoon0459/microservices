package com.example.authApp.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.authApp.domain.AbstractPersistableEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditableEntity<U, ID> extends AbstractPersistableEntity<ID> implements Serializable {
	

	private static final long serialVersionUID = -761503632186596342L;

    @CreatedDate
    LocalDate createdDate;

    @LastModifiedDate
    LocalDate lastModifiedDate;

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "created_by")
    U createdBy;

    @LastModifiedBy
    @ManyToOne
    @JoinColumn(name = "last_modified_by")
    U lastModifiedBy;
}
