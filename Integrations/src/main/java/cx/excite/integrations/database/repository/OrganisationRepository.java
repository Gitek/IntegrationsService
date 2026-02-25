package cx.excite.integrations.database.repository;

import cx.excite.integrations.database.entity.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrganisationRepository extends JpaRepository<Organisation, Integer> {
    Optional<Organisation> findOrganisationById(Integer id);
    List<Organisation> findOrganisationsByOkeyParentAndOrgStatus(Integer okeyParent, int orgStatus);
    List<Organisation> findOrganisationsByOkeyParent(Integer okeyParent);
}