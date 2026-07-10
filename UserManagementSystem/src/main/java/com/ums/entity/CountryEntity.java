package com.ums.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "country_master")
public class CountryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer countryId;

	@Column(nullable = false)
	private String countryName;

}
