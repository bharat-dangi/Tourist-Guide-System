package com.minorproject.tourist.guide.serviceImpl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.minorproject.tourist.guide.model.Places;
import com.minorproject.tourist.guide.repository.PlacesRepository;
import com.minorproject.tourist.guide.service.PlacesService;

@Service
public class PlaceServiceImpl implements PlacesService {

	@Autowired
	private PlacesRepository placesRepository;

	@Override
	public List<Places> findAll() {
		return placesRepository.findAll();
	}

	@Override
	public Places findById(int theId) {
	Optional<Places> result=placesRepository.findById(theId);
		Places thePlaces=null;
		if(result.isPresent()) {
			thePlaces=result.get();
		}
		else {
			throw new RuntimeException("Did not find place id-"+theId);
		}
		return thePlaces;
	}

	

	@Override
	public void save(Places places) {
	placesRepository.save(places);
		
	}

}
