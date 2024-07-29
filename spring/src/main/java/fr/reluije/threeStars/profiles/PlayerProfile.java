package fr.reluije.threeStars.profiles;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "player_profiles")
public class PlayerProfile {

    @Id
    @GeneratedValue
    private Long id;

    @JdbcType(VarcharJdbcType.class)
    @Column(nullable = false, unique = true)
    private UUID uniqueId;

    @Column(nullable = false)
    private String name;

}
