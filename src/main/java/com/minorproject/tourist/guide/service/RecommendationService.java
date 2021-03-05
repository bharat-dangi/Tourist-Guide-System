package com.minorproject.tourist.guide.service;

import java.util.List;

import com.minorproject.tourist.guide.model.Places;

public interface RecommendationService {
	List<Places> getTopRecommended(long userId,int howMuch);

	

}
