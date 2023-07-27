package com.vaadin.TelrosTestCase.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Entity
@ToString
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    @Getter
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
