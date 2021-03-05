package com.minorproject.tourist.guide.web;


import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.minorproject.tourist.guide.model.Rating;
import com.minorproject.tourist.guide.model.User;
import com.minorproject.tourist.guide.repository.UserRepository;
import com.minorproject.tourist.guide.service.PlacesService;
import com.minorproject.tourist.guide.service.RatingService;
import com.minorproject.tourist.guide.service.RecommendationEvaluatorService;
import com.minorproject.tourist.guide.service.RecommendationService;
import com.minorproject.tourist.guide.service.UserService;



@Controller
public class UserController {

	@Autowired
	RecommendationService recommendationService;
	
	@Autowired
	RatingService ratingService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;
	
	@Autowired
	PlacesService placesService;
	
	@Autowired
	RecommendationEvaluatorService evaluator;
	
	private int randomPlaceId;

	// USER PART
	// To redirect user to home page after login
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String userHome() {
		return "user/index";
	}

	@RequestMapping(value = "/rmse", method = RequestMethod.GET)
	public String weather(Model thModel) {
		thModel.addAttribute("evaluations",evaluator.getRMSE());

		return "user/rmse";
	}

	@RequestMapping(value = "/currency", method = RequestMethod.GET)
	public String currencyConverter() {
		return "user/currency";
	}

	@RequestMapping(value = "/clock", method = RequestMethod.GET)
	public String worldClock() {
		return "user/world-clock";
	}

	@RequestMapping(value = "/best", method = RequestMethod.GET)
	public String bestPlace(Model theModel) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();

		User user = userRepository.findByUsername(userDetail.getUsername());
		long theId = user.getId();


		theModel.addAttribute("places", recommendationService.getTopRecommended(theId, 5));
		
		return "user/best";
	}

	//To show rating form
	@RequestMapping(value = "/rating", method = RequestMethod.GET)
	public String ratePlace(Model theModel) { 

		int ranId=(int) ((Math.random()*(10-1))+1);
	
		theModel.addAttribute("rating",new Rating());
		 this.randomPlaceId=ranId;
		theModel.addAttribute("place",placesService.findById(randomPlaceId));
		
		return "user/rating";
	}
	
	
	//To save ratings
	@PostMapping(value="/rating")
	public String postRati(@ModelAttribute("rating")Rating rating  ){

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		System.out.println(rating.getPreference());

		User user = userRepository.findByUsername(userDetail.getUsername());
		int theId =(int) user.getId();
		

		
		ratingService.save(rating,randomPlaceId, theId);
		
	return "redirect:/rating?success=true";
			}
}