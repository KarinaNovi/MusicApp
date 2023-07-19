package com.novi.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    @Column(name = "instrument_name", length = 256, nullable = false)
    @NotNull
    private String instrumentName;

    @ManyToMany
    private List<Group> groups;

    @ManyToMany
    private List<Location> locations;

    @ManyToMany
    private List<User> users;
}
