package com.ums.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ums.entity.CountryEntity;

public interface CountryRepository extends JpaRepository<CountryEntity, Integer> {

}
