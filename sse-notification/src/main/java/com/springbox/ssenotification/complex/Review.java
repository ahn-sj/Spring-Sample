package com.springbox.ssenotification.complex;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    private String content;

    public Review(String content) {
        this.content = content;
    }
}
