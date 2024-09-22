package org.inventory.appuser.user.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@Table(name = "team_membership")
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class TeamMembership {

    @Id
    private int membershipId;

    private LocalDateTime joinedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private AppUser appUser;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Override
    public String toString() {
        return "TeamMembership{" +
                "membershipId=" + membershipId +
                ", joinedAt=" + joinedAt +
                ", team=" + team +
                ", role=" + role +
                '}';
    }
}
