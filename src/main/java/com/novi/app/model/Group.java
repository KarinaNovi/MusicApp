package com.novi.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.novi.app.util.Constants;
import com.novi.app.util.UserUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "groups")
@Setter
@Getter
@NoArgsConstructor
// @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
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
    @Column(name = "group_description", length = 4000)
    private String description;
    @Column(name = "registration_date")
    private Date registrationDate;
    @Column(name = "deletion_date")
    private Date deletionDate;
    @Column(name = "leader_id", nullable = false)
    private long leaderId;
    @Column(name = "repetition_dtm")
    private Date repetitionDtm;
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
            name = "locations_groups",
            joinColumns = { @JoinColumn(name = "group_id") },
            inverseJoinColumns = { @JoinColumn(name = "location_id") }
    )
    private List<Location> locations;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "music_instruments_groups",
            joinColumns = { @JoinColumn(name = "group_id") },
            inverseJoinColumns = { @JoinColumn(name = "instrument_id") }
    )
    private List<MusicInstrument> musicInstruments;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "music_styles_groups",
            joinColumns = { @JoinColumn(name = "group_id") },
            inverseJoinColumns = { @JoinColumn(name = "style_id") }
    )
    private List<MusicStyle> musicStyles;

    public Group(@NotNull String groupName,
                 int maxQuantity,
                 int currentQuantity,
                 String description,
                 Date repetitionDtm,
                 String password,
                 long leaderId) {
        this.groupName = groupName;
        this.maxQuantity = maxQuantity;
        this.currentQuantity = currentQuantity;
        this.description = description;
        this.registrationDate = new Date();
        this.deletionDate = new Date(Constants.MAX_DATE);
        this.repetitionDtm = repetitionDtm;
        this.password = UserUtil.encryptPassword(password);
        this.leaderId = leaderId;
    }
}
