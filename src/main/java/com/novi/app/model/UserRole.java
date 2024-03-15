package com.novi.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@Schema(description = "Словарь возможных ролей пользователей")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Идентификатор")
    private long roleId;

    @Schema(description = "Тип роли")
    @NotBlank
    @Size(min = 1, max = 50)
    @Column(name = "role_name", length = 50, nullable = false)
    private String roleName;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },mappedBy = "userRoles")
    @JsonIgnore
    private List<User> users;

    public UserRole(@NotNull String roleName) {
        this.roleName = roleName;
    }
}