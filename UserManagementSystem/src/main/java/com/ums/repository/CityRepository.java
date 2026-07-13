package com.ums.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ums.entity.CityEntity;

public interface CityRepository extends JpaRepository<CityEntity, Integer> {

	List<CityEntity> findByStateStateId(Integer stateId);
}
