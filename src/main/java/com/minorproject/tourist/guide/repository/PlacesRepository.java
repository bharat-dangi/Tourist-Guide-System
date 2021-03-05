package com.minorproject.tourist.guide.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.minorproject.tourist.guide.model.Places;

@Repository
public interface PlacesRepository extends JpaRepository<Places, Integer> {

	

}
