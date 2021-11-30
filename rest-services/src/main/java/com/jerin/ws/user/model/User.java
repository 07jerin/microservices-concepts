package com.jerin.ws.user.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Schema(description = "User entity")

@Entity
@Table
public class User {

	@Id
	@GeneratedValue(generator = "user_id_sequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(initialValue = 10, name = "user_id_sequence", sequenceName = "user_id_sequence")
	private Integer id;

	@Size(min = 3, message = "Name Should be atleast 3 letter")
	private String name;

	@Past
	@Schema(description = "Birthday should be in the past")
	private LocalDate birthday;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY) // Mapping field in the post entity
	private List<Post> posts;

}
