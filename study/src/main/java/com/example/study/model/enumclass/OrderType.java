package com.example.study.model.enumclass;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum OrderType {
    ALL(0, "BUNDLE", "All items are packaged into one bundle"),
    EACH(1, "INDIVIDUAL", "Each items are packaged individually");

    private Integer id;
    private String title;
    private String description;
}
