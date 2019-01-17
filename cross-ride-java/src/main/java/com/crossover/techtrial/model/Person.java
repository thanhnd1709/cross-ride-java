/**
 * 
 */
package com.crossover.techtrial.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * @author crossover
 *
 */
@Entity
@Table(name = "person")
public @Data class Person implements Serializable {

	private static final long serialVersionUID = 7401548380514451401L;

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(name = "name")
	@NotNull
	@Size(max=255)
	String name;

	@NotNull
	@Email
	@Column(name = "email")
	@Size(max=255)
	String email;

	@Column(name = "registration_number")
	@Size(max=255)
	String registrationNumber;

	public Person(@NotNull Long id,@NotNull String name, @NotNull @Email String email, String registrationNumber) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.registrationNumber = registrationNumber;
	}

	public Person() {
	}

}
