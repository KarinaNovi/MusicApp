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
    @Column(name = "style_name")
    private String styleName;

    @ManyToMany
    private List<User> users;

    @ManyToMany
    private List<Group> groups;
}
