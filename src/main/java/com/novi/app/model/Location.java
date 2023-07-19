package com.novi.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

@Entity
@Table(name = "location")
@Setter
@Getter
@ToString
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

    @ManyToMany
    private List<Group> groups;

    @ManyToMany
    private List<MusicInstrument> musicInstruments;
}
