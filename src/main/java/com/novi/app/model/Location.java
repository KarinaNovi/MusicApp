package com.novi.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "locations")
@Setter
@Getter
@NoArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long locationId;
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
    @Column(name = "repetition_dtm")
    private Date repetitionDtm;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },mappedBy = "locations")
    @JsonIgnore
    private List<Group> groups;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "music_instruments_locations",
            joinColumns = { @JoinColumn(name = "location_id") },
            inverseJoinColumns = { @JoinColumn(name = "instrument_id") }
    )
    private List<MusicInstrument> musicInstruments;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "music_styles_locations",
            joinColumns = { @JoinColumn(name = "location_id") },
            inverseJoinColumns = { @JoinColumn(name = "style_id") }
    )
    private List<MusicStyle> musicStyles;

    public Location(@NotNull String locationName,
                    double xCoordinate,
                    double yCoordinate,
                    String description,
                    double price,
                    Date repetitionDtm) {
        this.locationName = locationName;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.description = description;
        this.price = price;
        this.repetitionDtm = repetitionDtm;
    }
}
