package com.jerin.ws.user.model;

import java.time.LocalDate;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Schema(description = "User entity")
public class User {

	private Integer id;

	@Size(min = 3, message = "Name Should be atleast 3 letter")
	private String name;

	@Past
	@Schema(description = "Birthday should be in the past")
	private LocalDate birthday;

}
