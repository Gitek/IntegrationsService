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
@Table(schema= "dbo", name = "PortalProduct")
public class PortalProduct {
    @Id
    @Column(name = "ppkey")
    private int id;
    @Column(name = "Productid")
    private int productId;
    @Column(name = "ProductName")
    private String productName;
    @Column(name = "Inherit")
    private int inherit;
}

