package com.jerin.ws.user.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Table
@Getter
@Setter
public class Post {

	@Id
	@GeneratedValue(generator = "post_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "post_seq", sequenceName = "post_seq", initialValue = 100)
	private Integer id;

	@NotBlank
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@Getter(value = AccessLevel.NONE)
	private User user;

}
