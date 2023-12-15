package com.example.springEcommerce.model.entity;


import com.example.springEcommerce.util.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;


@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String phone;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    private int age;
    private String code;
    @Column(name = "is_active")
    private boolean isActive;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "is_verified")
    private boolean isVerified;
}
