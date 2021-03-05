package com.minorproject.tourist.guide.serviceImpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;

import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;

import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.minorproject.tourist.guide.model.Places;
import com.minorproject.tourist.guide.model.Rating;
import com.minorproject.tourist.guide.repository.RatingRepository;
import com.minorproject.tourist.guide.service.PlacesService;
import com.minorproject.tourist.guide.service.RecommendationService;

@Component
public class RecommendationServiceImpl implements RecommendationService {

	@Autowired
	private PlacesService placesService;

	@Autowired
	private RatingRepository ratingRepository;

	@Override
	public List<Places> getTopRecommended(long userId, int howMuch) {
		List<RecommendedItem> recommendedItems = new ArrayList<RecommendedItem>();
		List<Places> recommendedPlaces = new ArrayList<Places>();

		DataModel model = null;

		// Rating file creation from database.
		List<Rating> ratings = (List<Rating>) ratingRepository.findAll();
		String path = "ratings.txt";
		FileWriter writer = null;
		try {
			writer = new FileWriter(path, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<String> data = new ArrayList<String>();
		String temp;
		for (Rating preference : ratings) {
			temp = Integer.toString((int) preference.getUserId()) + ","
					+ Integer.toString((int) preference.getPlaceId()) + ","
					+ Double.toString(preference.getPreference());
			data.add(temp);
		}
		try {
			for (String element : data) {
				writer.write(element);
				writer.append('\n');
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		File file = new File(path);

		// Creating dataModel

		try {
			model = new FileDataModel(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Creating UserSimilarity object.
		UserSimilarity userSimilarity = null;
		try {
			userSimilarity = new UncenteredCosineSimilarity(model);
		} catch (TasteException e) {
			e.printStackTrace();
		}
		// Creating UserNeighbourHHood object.
		UserNeighborhood userNeighborhood=null;
		try {
			 userNeighborhood = new NearestNUserNeighborhood(10, userSimilarity, model);
		} catch (TasteException e) {
	e.printStackTrace();
		}


		// Create UserRecomender
		UserBasedRecommender recommender = new GenericUserBasedRecommender(model, userNeighborhood, userSimilarity);

		try {
			recommendedItems = recommender.recommend(userId, howMuch);
			if (!recommendedItems.isEmpty()) {
				for (RecommendedItem item : recommendedItems) {
					Long longValue = item.getItemID();
					int itemId = longValue.intValue();
					System.out.println(itemId);
					Places places = placesService.findById(itemId);
					recommendedPlaces.add(places);
				}
			}

		} catch (TasteException e) {
			e.printStackTrace();
		}

		return recommendedPlaces;
	}

}
