package com.minorproject.tourist.guide.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.minorproject.tourist.guide.model.Role;
import com.minorproject.tourist.guide.model.User;
import com.minorproject.tourist.guide.repository.RoleRepository;
import com.minorproject.tourist.guide.repository.UserRepository;
import com.minorproject.tourist.guide.service.UserService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void save(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setStatus("ENABLED");
		Role userRole = roleRepository.findByRole("USER");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User findById(int theId) {
		Optional<User> result=userRepository.findById(theId);
		
		
		User theUser=null;
		
		if(result.isPresent()) {
			theUser=result.get();
			
		}
		else {
			throw new RuntimeException("Did not find user id-"+theId);
		}
		return theUser;
		
	}

	@Override
	public void deleteById(int theId) {
		userRepository.deleteById(theId);
		
	}

}