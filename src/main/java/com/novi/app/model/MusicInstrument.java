package com.novi.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "music_instruments")
@Setter
@Getter
@NoArgsConstructor
public class MusicInstrument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int instrumentId;
    @Column(name = "instrument_name", length = 256, nullable = false)
    @NotNull
    private String instrumentName;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },mappedBy = "musicInstruments")
    @JsonIgnore
    private Set<Group> groups;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },mappedBy = "musicInstruments")
    @JsonIgnore
    private Set<Location> locations;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },mappedBy = "musicInstruments")
    @JsonIgnore
    private Set<User> users;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "music_styles_instruments",
            joinColumns = { @JoinColumn(name = "instrument_id") },
            inverseJoinColumns = { @JoinColumn(name = "style_id") }
    )
    private List<MusicStyle> musicStyles;

    public MusicInstrument(@NotNull String instrumentName) {
        this.instrumentName = instrumentName;
    }
}
