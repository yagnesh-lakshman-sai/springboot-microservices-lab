package com.ums.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "city_master")
public class CityEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cityId;

	private String cityName;

	@ManyToOne
	@JoinColumn(name = "state_id")
	private StateEntity state;

}
