package com.gdpr.unir.controller;

import com.gdpr.unir.model.Cities;
import com.gdpr.unir.model.User;
import com.gdpr.unir.repository.CitiesRepository;
import com.gdpr.unir.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.ObjectUtils.isEmpty;

@Controller
public class GdprFullDeleteController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CitiesRepository citiesRepository;

    @GetMapping("/gdpr-full-delete")
    public String fullDelete(Authentication authentication) {
        Optional<User> optionalUser = userRepository.findByUserName(authentication.getName());

        if (optionalUser.isPresent()) {
            com.gdpr.unir.model.User findUser = optionalUser.get();
            List<Cities> userReservations = citiesRepository.findAllByUserId(findUser.getId());

            if (!isEmpty(userReservations)) {
                citiesRepository.deleteAll(userReservations);
            }

            userRepository.delete(findUser);
        }

        return "gdpr-full-delete";
    }
}
