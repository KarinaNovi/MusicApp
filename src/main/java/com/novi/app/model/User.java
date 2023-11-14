package com.novi.app.model;

import com.novi.app.util.Constants;
import com.novi.app.util.UserUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
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
    private String userLogin;
    @Column(name = "password", length = 256, nullable = false)
    @NotNull
    private String password;
    @Column(name = "registration_dtm")
    private Date registrationDtm;
    @Column(name = "deletion_dtm")
    private Date deletionDtm;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "groups_users",
            joinColumns = { @JoinColumn(name = "users_user_id") },
            inverseJoinColumns = { @JoinColumn(name = "groups_group_id") }
    )
    private Set<Group> groups = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "music_instruments_users",
            joinColumns = { @JoinColumn(name = "users_user_id") },
            inverseJoinColumns = { @JoinColumn(name = "music_instruments_instrument_id") }
    )
    private Set<MusicInstrument> musicInstruments = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "music_style_users",
            joinColumns = { @JoinColumn(name = "users_user_id") },
            inverseJoinColumns = { @JoinColumn(name = "music_style_style_id") }
    )
    private Set<MusicStyle> musicStyles = new HashSet<>();

    public User(@NotNull String firstName,
                @NotNull String lastName,
                String middleName,
                String phoneNumber,
                @NotNull String email,
                String userLogin,
                @NotNull String password,
                @NotNull String birthday) {
        this.firstName = UserUtil.formatName(firstName);
        this.lastName = UserUtil.formatName(lastName);
        this.middleName = UserUtil.formatName(middleName);
        this.phoneNumber = UserUtil.formatPhoneNumber(phoneNumber);
        this.email = email;
        this.userLogin = UserUtil.formatUserLogin(userLogin);
        this.password = UserUtil.encryptPassword(password);
        // TODO: format will be updated on UI side?
        this.birthday = birthday;
        this.registrationDtm = new Date();
        this.deletionDtm = new Date(Constants.MAX_DATE);
    }
}