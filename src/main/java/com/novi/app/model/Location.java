package com.novi.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

@Entity
@Table(name = "location")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int locationId;
    @Column(name = "location_name", length = 256, nullable = false)
    @NotNull
    private String locationName;
    @Column(name = "x_coordinate", nullable = false)
    private double xCoordinate;
    @Column(name = "y_coordinate", nullable = false)
    private double yCoordinate;
    @Column(name = "description", length = 4000)
    private String description;
    @Column(name = "price")
    private double price;
    @Column(name = "repetition_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "dd.MM.yyyy'T'HH:mm:ss.SSSXXX")
    private String repetitionDate;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },mappedBy = "locations")
    @JsonIgnore
    private List<Group> groups;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "music_instruments_location",
            joinColumns = { @JoinColumn(name = "location_location_id") },
            inverseJoinColumns = { @JoinColumn(name = "music_instruments_instrument_id") }
    )
    private List<MusicInstrument> musicInstruments;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "music_style_location",
            joinColumns = { @JoinColumn(name = "location_location_id") },
            inverseJoinColumns = { @JoinColumn(name = "music_style_style_id") }
    )
    private List<MusicStyle> musicStyles;
}
