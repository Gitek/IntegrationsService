package cx.excite.integrations.database.repository;

import cx.excite.integrations.database.entity.DataOwnerPortalProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DataOwnerPortalProductRepository extends JpaRepository<DataOwnerPortalProduct, Integer> {
    List<DataOwnerPortalProduct> findDataOwnerPortalProductByOkey(int okey);

    Optional<DataOwnerPortalProduct> getDataOwnerPortalProductByProductIdAndOkey(int produktId, int okey);

    @Query(value = "select distinct d.* from DataOwnerPortalProduct d left join Organisation o on d.Okey=o.OKey where odkey=0 and (OrgStatus in (2,5) or OrgName is null)", nativeQuery = true)
    List<DataOwnerPortalProduct> getLicensesForDisabledOrgs();

    @Query(value = "select p.* from DataOwnerPortalProduct p inner join (select ProductId, okey from DataOwnerPortalProduct where odkey=0 group by ProductId, okey having count(*)>1) h on p.ProductId=h.ProductId and p.Okey=h.Okey order by okey", nativeQuery = true)
    List<DataOwnerPortalProduct> getLicenseDuplicates();

    @Query(value = "select * from DataOwnerPortalProduct where odkey=0 and ProductId = :ProductId and Okey = :Okey order by dpkey asc", nativeQuery = true)
    List<DataOwnerPortalProduct> getLicenseDuplicates(@Param("ProductId") Integer ProductId, @Param("Okey") Integer Okey);

    @Query(value = "select distinct okey from DataOwnerPortalProduct where odkey=0", nativeQuery = true)
    List<Integer> getOrgs();

    @Query(value = "select distinct okey from DataOwnerPortalProduct d inner join PortalProduct p on d.ProductId=p.ProductId where p.ProductName=:ProductName", nativeQuery = true)
    List<Integer> getLicenseOkeys(@Param("ProductName") String ProductName);
}