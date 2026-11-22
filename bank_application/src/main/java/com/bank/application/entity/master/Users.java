package com.bank.application.entity.master;

import java.time.LocalDate;

import com.bank.application.entity.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users", schema = "bank_schema")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Users extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_name", nullable = false)
	private String userName;

	@Column(name = "user_customer_id")
	private String userCustomerId;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "mobile_no", nullable = false, unique = true)
	private String mobileNo;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "date_of_birth", nullable = false)
	private LocalDate dateOfBirth;

	@Column(name = "role", nullable = false)
	private String role;

	@Column(name = "gender", nullable = false)
	private String gender;

	@Column(name = "address", nullable = false)
	private String address;

	@Column(name = "user_is_active")
	private boolean userIsActive;

	@Column(name = "migrated_user", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
	private boolean migratedUser = false;

	@Column(nullable = false, length = 36)
	private String batchId;

	@Lob
	@Column(name = "pay_load", columnDefinition = "LONGTEXT")
	private String payload;

}
