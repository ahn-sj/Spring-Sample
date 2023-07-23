package com.springbox.ssenotification.complex.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue
    private Long id;

    private String name;

    public Member(String name) {
        this.name = name;
    }

    @Builder
    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
