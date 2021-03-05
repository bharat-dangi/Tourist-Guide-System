package com.minorproject.tourist.guide.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.minorproject.tourist.guide.model.Rating;

@Repository
public interface RatingRepository extends CrudRepository<Rating, Integer> {

	

}
