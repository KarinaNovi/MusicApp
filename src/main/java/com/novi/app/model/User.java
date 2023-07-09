package com.novi.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;
    @Column(name = "first_name")
    @NotNull
    private String firstName;
    @Column(name = "last_name")
    @NotNull
    private String lastName;
    @Column(name = "middle_name")
    @NotNull
    private String middleName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "email")
    @NotNull
    private String email;
    @Column(name = "md5")
    @NotNull
    private String md5;
    @Column(name = "birthday")
    @NotNull
    private String birthday;
    @Column(name = "registration_date")
    private String registrationDate;
    @Column(name = "deleted_date")
    private String deletedDate;
    @Column(name = "user_login")
    private String userLogin;


    @ManyToMany
    private List<Group> groups;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Group group;

    @ManyToMany
    private List<MusicInstrument> musicInstruments;

    @ManyToMany
    private List<MusicStyle> musicStyles;

    @Override
    public String toString() {
        return String.format(
                "User[id=%d, firstName='%s', lastName='%s', phoneNumber='%s', email='%s']",
                userId, firstName, lastName, phoneNumber, email);
    }

    public User(@NotNull String firstName, @NotNull String lastName, String phoneNumber, @NotNull String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}