package com.ertanAlabay.deneme_2.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DtoEvent {
	private String name;
    private String description;
    private String category;
    private LocalDateTime date;
    private String location;
}
