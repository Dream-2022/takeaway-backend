package com.example.takeawaybackend.tool;

import lombok.Data;

@Data
public class DataToFront <T> {
    private int code;
    private T data;
    private String message;


}
