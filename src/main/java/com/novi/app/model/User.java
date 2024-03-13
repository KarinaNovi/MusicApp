package com.novi.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.novi.app.util.Constants;
import com.novi.app.util.UserUtil;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@Schema(description = "Информация о пользователе")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Идентификатор")
    private long userId;

    @Schema(description = "Имя")
    @NotBlank
    @Size(min = 1, max = 50)
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Schema(description = "Фамилия")
    @NotBlank
    @Size(min = 1, max = 50)
    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Schema(description = "Отчество")
    @Size(min = 1, max = 100)
    @Column(name = "middle_name", length = 100)
    private String middleName;

    @Schema(description = "Номер телефона")
    @Size(min = 1, max = 20)
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Schema(description = "E-mail")
    @NotBlank
    @Size(min = 1, max = 64)
    @Column(name = "email", length = 64, nullable = false)
    private String email;

    @Schema(description = "Дата рождения")
    @NotBlank
    @Column(name = "birthday", nullable = false)
    private String birthday;

    @Schema(description = "Логин")
    @Column(name = "user_login", unique=true)
    private String userLogin;

    @Schema(description = "Пароль")
    @NotBlank
    @Size(min = 1, max = 256)
    @Column(name = "password", length = 256, nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Schema(description = "Дата регистрации")
    @Column(name = "registration_date")
    private Date registrationDate;

    @Schema(description = "Дата удаления")
    @Column(name = "deletion_date")
    private Date deletionDate;

    @Schema(description = "Роль")
    @Column(name = "user_role", length = 10, nullable = false)
    private String userRole;

    @Hidden
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "groups_users",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "group_id") }
    )
    private Set<Group> groups = new HashSet<>();

    @Hidden
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "music_instruments_users",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "instrument_id") }
    )
    private Set<MusicInstrument> musicInstruments = new HashSet<>();

    @Hidden
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "music_styles_users",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "style_id") }
    )
    private Set<MusicStyle> musicStyles = new HashSet<>();

    // If you have multiple constructors in your class, you have to use the @Autowired annotation
    // to define which constructor is used for dependency injection:
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
        this.userRole = String.valueOf(UserRole.USER);
        // TODO: format will be updated on UI side?
        this.birthday = birthday;
        this.registrationDate = new Date();
        this.deletionDate = new Date(Constants.MAX_DATE);
    }

    @ToString.Include(name = "password")
    private String maskPassword() {
        return "********";
    }
}