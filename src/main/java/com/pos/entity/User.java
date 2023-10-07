package com.pos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id;
	
	@Column(name="username")
	private String username;
	
	@Column(name="phone")
	private Integer phone;
	
	@Column(name="address")
	private String address;
	
	@Column(name="password")
	private String password;
}
