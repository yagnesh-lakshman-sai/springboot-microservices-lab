package com.ums.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ums.entity.StateEntity;

public interface StateRepository extends JpaRepository<StateEntity, Integer> {

	List<StateEntity> findByCountryCountryId(Integer countryId);
}
