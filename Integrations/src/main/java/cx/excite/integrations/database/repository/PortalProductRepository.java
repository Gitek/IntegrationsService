package cx.excite.integrations.database.repository;

import cx.excite.integrations.database.entity.PortalProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PortalProductRepository extends JpaRepository<PortalProduct, Integer> {
    @Query(value = "select * from PortalProduct where Inherit=1", nativeQuery = true)
    List<PortalProduct> getInheritLicenses();
}