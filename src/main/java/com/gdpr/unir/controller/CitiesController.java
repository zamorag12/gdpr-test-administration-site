package com.gdpr.unir.controller;

import com.gdpr.unir.repository.CitiesRepository;
import com.gdpr.unir.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class CitiesController {
    @Autowired
    private CitiesRepository citiesRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/cities/list")
    public String city(Model model, Authentication authentication, @PageableDefault(size = 5) Pageable pageable,
                       @RequestParam(name = "value", required = false) String value) {
        Long userId = null;

        Optional<com.gdpr.unir.model.User> optionalUser = userRepository.findByUserName(authentication.getName());

        if (optionalUser.isPresent()) {
            com.gdpr.unir.model.User findUser = optionalUser.get();
            userId = findUser.getId();
        }

        if (value != null) {
            model.addAttribute("key", value);
            model.addAttribute("data", citiesRepository.findByNameContainingIgnoreCaseAndUserId(value, userId, pageable));
        } else {
            model.addAttribute("data", citiesRepository.findAllByUserId(userId, pageable));
        }

        return "cities/list";

    }
}
