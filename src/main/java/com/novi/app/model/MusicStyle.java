package com.novi.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "music_styles")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MusicStyle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int styleId;
    @Column(name = "style_name", length = 256, nullable = false)
    @NotNull
    private String styleName;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },mappedBy = "musicStyles")
    @JsonIgnore
    private List<User> users;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },mappedBy = "musicStyles")
    @JsonIgnore
    private List<Group> groups;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },mappedBy = "musicStyles")
    @JsonIgnore
    private List<Location> locations;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },mappedBy = "musicStyles")
    @JsonIgnore
    private List<MusicInstrument> musicInstruments;

    public MusicStyle(@NotNull String styleName) {
        this.styleName = styleName;
    }
}
