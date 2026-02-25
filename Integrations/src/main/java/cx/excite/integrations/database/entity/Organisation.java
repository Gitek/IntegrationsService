package cx.excite.integrations.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(schema= "dbo", name = "Organisation")
public class Organisation {
    @Id
    @Column(name = "OKey")
    private int id;

    @Column(name = "OKeyParent")
    private int okeyParent;

    @Column(name = "OrgName", length = 100)
    private String orgName;

    @Column(name = "OrgStatus")
    private Integer orgStatus;

    @Column(name = "OrgType")
    private Integer orgType;
}