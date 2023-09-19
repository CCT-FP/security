package com.example.cct.Config;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class CommonResponse <T>{
    String message;
    int code;
}

