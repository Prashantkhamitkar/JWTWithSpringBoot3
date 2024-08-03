package com.hotelbooking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="hoteluser")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString(exclude ="password")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String email;
	private String password;
	@Enumerated(EnumType.STRING)
	private Role role;
	private String mobile;
	private String firstName;
	private String lastName;
	private String gender;
	
}
