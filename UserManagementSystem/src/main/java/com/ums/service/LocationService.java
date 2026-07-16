package com.ums.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ums.entity.CityEntity;
import com.ums.entity.CountryEntity;
import com.ums.entity.StateEntity;

@Service
public interface LocationService {

	List<CountryEntity> getAllCountries();

	List<StateEntity> getStates(Integer countryId);

	List<CityEntity> getCities(Integer stateId);
}
