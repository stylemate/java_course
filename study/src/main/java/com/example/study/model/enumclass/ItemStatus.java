package com.example.study.model.enumclass;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ItemStatus {
    REGISTERED(0, "REGISTERED", "Item registered"),
    UNREGISTERED(1, "UNREGISTERED", "Item unregistered"),
    PENDING(2, "PENDING", "Item pending for approval");

    private Integer Id;
    private String title;
    private String description;
}
