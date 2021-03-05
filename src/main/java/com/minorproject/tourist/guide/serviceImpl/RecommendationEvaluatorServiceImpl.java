package com.minorproject.tourist.guide.serviceImpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minorproject.tourist.guide.model.Rating;
import com.minorproject.tourist.guide.repository.RatingRepository;
import com.minorproject.tourist.guide.service.RecommendationEvaluatorService;

@Service
public class RecommendationEvaluatorServiceImpl implements RecommendationEvaluatorService {

	@Autowired
	private RatingRepository ratingRepository;

	@Override
	public double getRMSE() {
		DataModel model = null;
		
		//rating file creation
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

		try {
			model = new FileDataModel(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		RecommenderEvaluator evaluator = new RMSRecommenderEvaluator();
		RecommenderBuilder builder = new MyRecommenderBuilder();
		Double result = null;
		try {
			result = evaluator.evaluate(builder, null, model, 0.7, 0.3);
		} catch (TasteException e) {
			e.printStackTrace();
		}
		System.out.println(result);

		return result;
	}

}

class MyRecommenderBuilder implements RecommenderBuilder {

	public Recommender buildRecommender(DataModel dataModel) throws TasteException {
		UserSimilarity similarity = new UncenteredCosineSimilarity(dataModel);
		UserNeighborhood neighborhood = new NearestNUserNeighborhood(10, similarity, dataModel);
		return new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
	}

}
