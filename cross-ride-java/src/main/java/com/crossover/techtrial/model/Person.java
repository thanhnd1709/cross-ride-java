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
import javax.validation.constraints.NotNull;


/**
 * @author crossover
 *
 */
@Entity
@Table(name = "person")
public class Person implements Serializable{

  private static final long serialVersionUID = 7401548380514451401L;
  
  public Person() {}
 
  public Person(Long id, String name, String email, String registrationNumber) {
	// TODO Auto-generated constructor stub
	  this.id = id;
	  this.name = name;
	  this.email = email;
	  this.registrationNumber = registrationNumber;
}

  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "name")
  String name;
  @NotNull
  @Email
  @Column(name = "email")
  String email;

  @Column(name = "registration_number")
  String registrationNumber;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getRegistrationNumber() {
    return registrationNumber;
  }

  public void setRegistrationNumber(String registrationNumber) {
    this.registrationNumber = registrationNumber;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (id ^ (id >>> 32));
    return result;
  }
  /**
   * Refactor the equals methods
   */
  @Override
  public boolean equals(Object other) {
	  if (other == null) {
		  return false;
	  }
	  if (!(other instanceof Person)) {
		  return false;
	  } else {
		  Person person = (Person) other;
		  return this.getId() != null ? this.getId().equals(person.getId()) : false;
	  }
  }

  @Override
  public String toString() {
    return "Person [id=" + id + ", name=" + name + ", email=" + email + ", registrationNumber=" + registrationNumber + "]";
  }
  
  


}
