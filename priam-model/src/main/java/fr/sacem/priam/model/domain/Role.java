package fr.sacem.priam.model.domain;

import fr.sacem.priam.common.constants.RoleRight;
import fr.sacem.priam.common.constants.RoleType;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by embouazzar on 30/08/2017.
 */

@Entity
@Table(name = "PRIAM_ROLE")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
    private RoleType type;

    @Column(name = "EXTERNAL_ID")
    private String externalId;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = RoleRight.class, fetch = FetchType.EAGER)
    @CollectionTable(name="PRIAM_ROLE_RIGHTS", joinColumns=@JoinColumn(name="ROLE_ID"))
    @Column(name = "RIGHTS")
    private Set<RoleRight> rights;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public RoleType getType() {
        return type;
    }

    public void setType(RoleType type) {
        this.type = type;
    }


    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Set<RoleRight> getRights() {
        return rights;
    }

    public void setRights(Set<RoleRight> rights) {
        this.rights = rights;
    }
}
