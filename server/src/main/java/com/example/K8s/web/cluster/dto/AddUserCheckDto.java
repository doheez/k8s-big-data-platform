package com.example.K8s.web.cluster.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class AddUserCheckDto {
    private ArrayList<String> invalid_email = new ArrayList<>();
    private ArrayList<Long> valid_userId = new ArrayList<>();
}
