package com.ecommerce.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.entities.User;
import com.ecommerce.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	public UserServiceImpl() {
		log.info(getClass().getSimpleName() + " created.");
	}

	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean saveUser(User user, MultipartFile file) {

		if (!ObjectUtils.isEmpty(user)) {

			File saveFile = null;
			try {
				saveFile = new ClassPathResource("static/images").getFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

			Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "profile_img" + File.separator
					+ file.getOriginalFilename());

			// System.out.println(path);
			log.info("path to image save: " + path);

			try {
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}

			userRepository.save(user);
			log.info("product with id: " + user.getId() + " saved");

			return true;
		}

		return false;
	}

}
