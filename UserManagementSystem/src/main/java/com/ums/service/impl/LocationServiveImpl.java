package com.ums.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ums.entity.CityEntity;
import com.ums.entity.CountryEntity;
import com.ums.entity.StateEntity;
import com.ums.repository.CityRepository;
import com.ums.repository.CountryRepository;
import com.ums.repository.StateRepository;
import com.ums.service.LocationService;

@Service
public class LocationServiveImpl implements LocationService {

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private CityRepository cityRepository;

	@Override
	public List<CountryEntity> getAllCountries() {

		return countryRepository.findAll();
	}

	@Override
	public List<StateEntity> getStates(Integer countryId) {

		return stateRepository.findByCountryCountryId(countryId);
	}

	@Override
	public List<CityEntity> getCities(Integer stateId) {

		return cityRepository.findByStateStateId(stateId);
	}

}
