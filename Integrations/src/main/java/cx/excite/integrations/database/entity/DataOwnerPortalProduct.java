package cx.excite.integrations.database.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(schema= "dbo", name = "DataOwnerPortalProduct")
public class DataOwnerPortalProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DataOwnerPortalProduct_SEQ")
    @SequenceGenerator(name = "DataOwnerPortalProduct_SEQ", allocationSize = 1)
    @Column(name = "dpkey")
    private int id;
    @Column(name = "Productid")
    private int productId;
    @Column(name = "OKey")
    private int okey;
    @Column(name = "Odkey")
    private int odkey;
}

