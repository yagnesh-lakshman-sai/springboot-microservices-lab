package com.ums.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "state_master")
public class StateEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer stateId;

	private String stateName;

	@ManyToOne
	@JoinColumn(name = "country_id")
	private CountryEntity country;

}