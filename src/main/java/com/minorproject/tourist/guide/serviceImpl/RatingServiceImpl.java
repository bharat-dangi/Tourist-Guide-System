package com.minorproject.tourist.guide.serviceImpl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minorproject.tourist.guide.model.Rating;

import com.minorproject.tourist.guide.repository.RatingRepository;
import com.minorproject.tourist.guide.service.RatingService;

@Service
public class RatingServiceImpl implements RatingService{
	
	@Autowired
	private RatingRepository ratingRepository;

	@Override
	public void save(Rating rating,int placeId, int userId) {	
		rating.setUserId(userId);
		rating.setPlaceId(placeId);
		ratingRepository.save(rating);
		
	}

	

}
