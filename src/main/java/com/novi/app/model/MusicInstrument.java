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
@Table(name = "music_instruments")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MusicInstrument {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int instrumentId;
    @Column(name = "instrument_name")
    private String instrumentName;

    @ManyToMany
    private List<Group> groups;

    @ManyToMany
    private List<Location> locations;

    @ManyToMany
    private List<User> users;
}
