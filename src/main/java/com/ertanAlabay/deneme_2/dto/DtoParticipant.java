package com.ertanAlabay.deneme_2.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DtoParticipant {
	
	private Long id;
    private String name;

    // Constructor
    public DtoParticipant(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter ve Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

