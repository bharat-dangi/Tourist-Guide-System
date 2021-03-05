package com.minorproject.tourist.guide.service;

import com.minorproject.tourist.guide.model.Rating;


public interface RatingService {
	   void save(Rating rating,int placeId,int userId);
}
