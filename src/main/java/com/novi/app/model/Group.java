package com.novi.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "groups")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int groupId;
    @Column(name = "group_name")
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
    @Column(name = "md5")
    private String md5;

    @ManyToMany(mappedBy = "users")
    List<User> users;

    @ManyToMany
    private List<Location> locations;

    @ManyToMany
    private List<MusicInstrument> musicInstruments;

    @ManyToMany
    private List<MusicStyle> musicStyles;
}
