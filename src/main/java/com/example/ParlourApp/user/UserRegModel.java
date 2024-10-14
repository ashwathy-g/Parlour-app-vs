package com.example.ParlourApp.user;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "USER_REG")
public class UserRegModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private  Integer id;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "password")
    private String password;

    @Column(name="phoneNumber")
    private String phoneNumber;

    @Column(name = "email")
    private String email;



    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private List<String> roles;

    @Transient
    private String token;

}
