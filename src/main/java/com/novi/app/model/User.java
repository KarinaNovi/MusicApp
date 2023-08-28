package com.novi.app.model;

import com.novi.app.util.Constants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
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
    @NotNull
    private String birthday;
    @Column(name = "user_login")
    @NotNull
    private String userLogin;
    @Column(name = "password", length = 256, nullable = false)
    @NotNull
    private String password;
    @Column(name = "registration_dtm")
    private String registrationDtm;
    @Column(name = "deletion_dtm")
    @DateTimeFormat(pattern = "dd.MM.yyyy'T'HH:mm:ssXXX")
    private String deletionDtm;


    @ManyToMany
    @JoinColumn(name = "group_id")
    private List<Group> groups;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Group group;

    @ManyToMany
    private List<MusicInstrument> musicInstruments;

    @ManyToMany
    private List<MusicStyle> musicStyles;

    public User(@NotNull String firstName,
                @NotNull String lastName,
                String middleName,
                String phoneNumber,
                @NotNull String email,
                String userLogin,
                @NotNull String password,
                @NotNull String birthday) {
        this.firstName = formatName(firstName);
        this.lastName = formatName(lastName);
        this.middleName = formatName(middleName);
        this.phoneNumber = formatPhoneNumber(phoneNumber);
        this.email = email;
        this.userLogin = formatUserLogin(userLogin);
        this.password = encryptPassword(password);
        // TODO: format will be updated on UI side?
        this.birthday = birthday;
        this.registrationDtm = formatDate(new Date());
        // creation - default max value, deletion - override to Sysdate
        this.deletionDtm = formatDate(new Date(Constants.MAX_DATE));
    }

    private String formatDate(Date inputDate) {
        SimpleDateFormat customDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return customDateFormat.format(inputDate);
    }

    private String formatName(String inputName) {
        return inputName == null
                ? null
                : inputName.substring(0, 1).toUpperCase() + inputName.substring(1);
    }

    private String formatPhoneNumber(String phoneNumber) {
        return phoneNumber.substring(0, 1).replaceAll("\\d", "+7") + " (" + phoneNumber.substring(1, 4) + ") " + phoneNumber.substring(4, 7) + " " + phoneNumber.substring(7, 9) + " " + phoneNumber.substring(9);
    }

    private String formatUserLogin(String userLogin) {
        return userLogin != null ? userLogin : String.valueOf(UUID.randomUUID());
    }

    private String encryptPassword(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }
}