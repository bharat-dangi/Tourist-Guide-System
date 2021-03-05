package com.minorproject.tourist.guide.web;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.minorproject.tourist.guide.model.Places;
import com.minorproject.tourist.guide.model.User;
import com.minorproject.tourist.guide.repository.PlacesRepository;
import com.minorproject.tourist.guide.service.UserService;


@Controller
public class AdminController {

	@Autowired
	private UserService userService;

	@Autowired
	private PlacesRepository placesRepository;

	// ADMIN PART
	// To redirect admin to admin homepage after login
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminHome() {
		return "admin/admin-dashboard";
	}

	@RequestMapping(value = "/user_detail", method = RequestMethod.GET)
	public String userDetail(Model theModel) {
		// get the users from the database
		List<User> theUser = userService.findAll();

		theModel.addAttribute("users", theUser);

		return "admin/user-detail";
	}

	// To show add place form
	@RequestMapping(value = "/add_place", method = RequestMethod.GET)
	public String addPlace(Model theModel) {
		theModel.addAttribute("places", new Places());
		return "admin/new-place";
	}

	// To save new place
	@RequestMapping(value = "/add_place", method = RequestMethod.POST,consumes = {"multipart/form-data"})
	public String addPlace(@ModelAttribute(name = "places") Places places, 
			@RequestParam("imageFile") MultipartFile file) throws IOException {  
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		places.setImageFile(fileName);
		Places savedPlaces = placesRepository.save(places);

		String uploadDir = "places-photos/" + savedPlaces.getId();
		
		Path uploadPath=Paths.get(uploadDir);
		
		   
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            System.out.println(filePath.toFile().getAbsolutePath());
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {        
            throw new IOException("Could not save image file: " + fileName);
        } 
		
	

		return "redirect:/add_place?register=true";
	}

	@RequestMapping(value = "/add_admin", method = RequestMethod.GET)
	public String addAdmin() {
		return "admin/add-admin";
	}

	@RequestMapping(value = "/update_place", method = RequestMethod.GET)
	public String updatePlace() {
		return "admin/update-place";
	}
}
