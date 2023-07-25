package com.vaadin.TelrosTestCase.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDate;

@Data
@Entity
@ToString
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String login;
    private String password;

    private String lastName;
    private String firstName;
    private String patronymic;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;
    private String email;
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "image")
    private Image image;

    @Enumerated(EnumType.STRING)
    private Role role;
}
