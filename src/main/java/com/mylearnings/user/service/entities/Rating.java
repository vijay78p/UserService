package com.mylearnings.user.service.entities;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rating {
    private String ratingId;
    private String UserId;
    private String hotelId;
    private int rating;
    private String feedback;
    private Hotel hotel ;
}
