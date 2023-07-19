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
@Table(name = "music_style")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MusicStyle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int styleId;
    @Column(name = "style_name", length = 256, nullable = false)
    @NotNull
    private String styleName;

    @ManyToMany
    private List<User> users;

    @ManyToMany
    private List<Group> groups;
}
