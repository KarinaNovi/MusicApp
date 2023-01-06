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
@Table(name = "location")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int locationId;
    @Column(name = "location_name")
    private String locationName;
    @Column(name = "x_coordinate")
    private double xCoordinate;
    @Column(name = "y_coordinate")
    private double yCoordinate;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private double price;
    @Column(name = "repetition_date")
    private String repetitionDate;

    @ManyToMany
    private List<Group> groups;

    @ManyToMany
    private List<MusicInstrument> musicInstruments;
}
