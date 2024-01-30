package com.project.stackoverflow.dto;

import lombok.Data;

import java.util.Set;

@Data
public class TagDTO {
    private Set<String> tags;
}