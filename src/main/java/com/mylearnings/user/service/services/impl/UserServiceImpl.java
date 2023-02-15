package com.mylearnings.user.service.services.impl;

import com.mylearnings.user.service.entities.Hotel;
import com.mylearnings.user.service.entities.Rating;
import com.mylearnings.user.service.entities.User;
import com.mylearnings.user.service.exceptions.ResourceNotFoundException;
import com.mylearnings.user.service.external.service.HotelService;
import com.mylearnings.user.service.repository.UserRepository;
import com.mylearnings.user.service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    private Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
    /**
     * @param user
     * @return
     */
    @Override
    public User saveUser(User user) {
        String randomUUID = UUID.randomUUID().toString();
        user.setUserId(randomUUID);
        return userRepository.save(user);
    }

    /**
     * @return
     */
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public User getUser(String userId) {
        logger.info("=== calling getUser method -- started =====");
        /** get user from database with the user repository **/

        User user=userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found for the given id"));
        logger.info("user {}", user.getUserId());

        /** get rating info from rating service **/
        logger.info("=== Initiating the Rating service call  =====");
        //Hotel hotel=hotelService.getHotel("2ca06701-b798-400c-9d60-9e928c28a25f");
        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + user.getUserId(), Rating[].class);
        List<Rating> requestedUserRating= Arrays.stream(ratingsOfUser).toList();

       List<Rating> ratingList= requestedUserRating.stream().map(rating ->{
           /** Resttemplate patten of calling the api service**/
                // ResponseEntity<Hotel> hotelEntity=restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(),Hotel.class);
               // Hotel hotel=hotelEntity.getBody();
           /** FienClient  patten of calling the api service by netflix**/
           Hotel hotel=hotelService.getHotel(rating.getHotelId());

            logger.info("entity ....{}",hotel);
            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);


        logger.info("==== calling getUser method -- ended =====");
        return user;
    }
}
