package com.mylearnings.user.service.external.service;

import com.mylearnings.user.service.entities.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "HOTEL-SERVICE")
public interface HotelService
{
    @GetMapping("hotels/{hotelId}")
    public Hotel getHotel(@PathVariable("hotelId") String hotelId);
}

