package com.example.projectshop.dto;

import lombok.Data;

import java.util.List;
@Data
public class UserDTO {
    private Integer id;
    private String email;
    private String password;
    private List<Integer> releIds;
    private String firstName;
    private String lastName;
}
