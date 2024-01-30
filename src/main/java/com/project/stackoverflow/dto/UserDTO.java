package com.project.stackoverflow.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {
    @JsonIgnore
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String username;
    private String password;
}
