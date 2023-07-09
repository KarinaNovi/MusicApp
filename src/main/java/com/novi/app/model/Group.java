package com.novi.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "groups")
@Setter
@Getter
@ToString
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int groupId;
    @Column(name = "group_name")
    @NotNull
    private String groupName;
    @Column(name = "max_quantity")
    private int maxQuantity;
    @Column(name = "current_quantity")
    private int currentQuantity;
    @Column(name = "who_in_desc")
    private String whoInDesc;
    @Column(name = "who_needs_desc")
    private String whoNeedsDesc;
    @Column(name = "registration_date")
    private String registrationDate;
    @Column(name = "deleted_date")
    private String deletedDate;
    @Column(name = "leader_id")
    private int leaderId;
    @Column(name = "repetition_date")
    private String repetitionDate;

    @ManyToMany
    List<User> users;

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

    public Group() {

    }
}
