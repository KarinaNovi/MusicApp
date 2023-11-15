package com.novi.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "groups")
@Setter
@Getter
@NoArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long groupId;
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
    private Date registrationDtm;
    @Column(name = "deleted_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "dd.MM.yyyy'T'HH:mm:ss.SSSXXX")
    private String deletionDtm;
    @Column(name = "leader_id", insertable=false, updatable=false)
    private long leaderId;
    @Column(name = "repetition_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "dd.MM.yyyy'T'HH:mm.SSSXXX")
    private String repetitionDate;
    @Column(name = "password", length = 256, nullable = false)
    @NotNull
    private String password;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },mappedBy = "groups")
    @JsonIgnore
    Set<User> users = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "location_groups",
            joinColumns = { @JoinColumn(name = "groups_group_id") },
            inverseJoinColumns = { @JoinColumn(name = "location_location_id") }
    )
    private List<Location> locations;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "music_instruments_groups",
            joinColumns = { @JoinColumn(name = "groups_group_id") },
            inverseJoinColumns = { @JoinColumn(name = "music_instruments_instrument_id") }
    )
    private List<MusicInstrument> musicInstruments;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "music_style_groups",
            joinColumns = { @JoinColumn(name = "groups_group_id") },
            inverseJoinColumns = { @JoinColumn(name = "music_style_style_id") }
    )
    private List<MusicStyle> musicStyles;

    public Group(@NotNull String groupName,
                 int maxQuantity,
                 int currentQuantity,
                 String whoInDesc,
                 String whoNeedsDesc,
                 Date registrationDtm,
                 String deletionDtm,
                 String repetitionDate) {
        this.groupName = groupName;
        this.maxQuantity = maxQuantity;
        this.currentQuantity = currentQuantity;
        this.whoInDesc = whoInDesc;
        this.whoNeedsDesc = whoNeedsDesc;
        this.registrationDtm = registrationDtm;
        this.deletionDtm = deletionDtm;
        this.repetitionDate = repetitionDate;
    }
}
