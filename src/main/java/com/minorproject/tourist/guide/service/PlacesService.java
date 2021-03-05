package com.minorproject.tourist.guide.service;

import java.util.List;

import com.minorproject.tourist.guide.model.Places;

public interface PlacesService {
	
	 List<Places> findAll();
	 Places findById(int theId);
	 void save(Places places);

}
