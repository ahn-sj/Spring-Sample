package com.springbox.ssenotification.simple;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HoppyEvent {

    private String type;
    private String url;
}