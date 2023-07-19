package com.novi.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int userId;
    @Column(name = "first_name", length = 50, nullable = false)
    @NotNull
    private String firstName;
    @Column(name = "last_name", length = 50, nullable = false)
    @NotNull
    private String lastName;
    @Column(name = "middle_name", length = 50)
    private String middleName;
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;
    @Column(name = "email", length = 64, nullable = false)
    @NotNull
    private String email;
    @Column(name = "birthday", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "dd.MM.yyyy")
    @NotNull
    private String birthday;
    @Column(name = "user_login")
    private String userLogin;
    @Column(name = "password", length = 256, nullable = false)
    @NotNull
    private String password;
    @Column(name = "registration_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "dd.MM.yyyy'T'HH:mm:ss.SSSXXX")
    private String registrationDate;
    @Column(name = "deleted_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "dd.MM.yyyy'T'HH:mm:ss.SSSXXX")
    private String deletedDate;


    @ManyToMany
    @JoinColumn(name = "group_id", nullable = false)
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