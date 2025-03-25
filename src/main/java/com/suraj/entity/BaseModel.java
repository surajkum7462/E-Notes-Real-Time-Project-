package com.suraj.entity;

import java.util.Date;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseModel {

	private Boolean is_Active;

	private Boolean is_Deleted;

	private Integer created_By;

	private Date created_On;

	private Integer updated_By;

	private Date updated_On;

}
