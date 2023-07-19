package com.novi.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

@Entity
@Table(name = "groups")
@Setter
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groupId;
    @Column(name = "group_name", length = 50, nullable = false)
    @NotNull
    private String groupName;
    @Column(name = "max_quantity")
    private int maxQuantity;
    @Column(name = "current_quantity")
    private int currentQuantity;
    @Column(name = "who_in_desc", length = 250)
    private String whoInDesc;
    @Column(name = "who_needs_desc", length = 4000)
    private String whoNeedsDesc;
    @Column(name = "registration_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "dd.MM.yyyy'T'HH:mm:ss.SSSXXX")
    private String registrationDate;
    @Column(name = "deleted_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "dd.MM.yyyy'T'HH:mm:ss.SSSXXX")
    private String deletedDate;
    @Column(name = "leader_id", insertable=false, updatable=false)
    private int leaderId;
    @Column(name = "repetition_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "dd.MM.yyyy'T'HH:mm.SSSXXX")
    private String repetitionDate;
    @Column(name = "password", length = 256, nullable = false)
    @NotNull
    private String password;

    @ManyToMany
    @JoinColumn(name = "user_id", nullable = false)
    List<User> users;

    @ManyToOne
    @JoinColumn(name = "leader_id", nullable = false)
    private User user;

    @ManyToMany
    private List<Location> locations;

    @ManyToMany
    private List<MusicInstrument> musicInstruments;

    @ManyToMany
    private List<MusicStyle> musicStyles;

    public Group(@NotNull String groupName,
                 int maxQuantity,
                 int currentQuantity,
                 String whoInDesc,
                 String whoNeedsDesc,
                 String registrationDate,
                 String deletedDate,
                 String repetitionDate) {
        this.groupName = groupName;
        this.maxQuantity = maxQuantity;
        this.currentQuantity = currentQuantity;
        this.whoInDesc = whoInDesc;
        this.whoNeedsDesc = whoNeedsDesc;
        this.registrationDate = registrationDate;
        this.deletedDate = deletedDate;
        this.repetitionDate = repetitionDate;
    }
}
