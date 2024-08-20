package com.example.habitbuilder.domain.bo;

import lombok.Data;

@Data
public class UserItemInteraction {
    private Long userId;
    private Long ItemId;
    private int rating;

    public UserItemInteraction(Long userId, Long ItemId, int rating) {
        this.userId = userId;
        this.ItemId = ItemId;
        this.rating = rating;
    }
}