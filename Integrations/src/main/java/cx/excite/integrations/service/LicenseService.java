package cx.excite.integrations.service;

import cx.excite.integrations.database.entity.DataOwnerPortalProduct;
import cx.excite.integrations.database.entity.Organisation;
import cx.excite.integrations.database.entity.PortalProduct;
import cx.excite.integrations.database.repository.DataOwnerPortalProductRepository;
import cx.excite.integrations.database.repository.OrganisationRepository;
import cx.excite.integrations.database.repository.PortalProductRepository;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class LicenseService {
    private final PortalProductRepository portalProductRepository;
    private final DataOwnerPortalProductRepository dataOwnerPortalProductRepository;
    private final OrganisationRepository organisationRepository;

    public LicenseService(PortalProductRepository portalProductRepository, DataOwnerPortalProductRepository dataOwnerPortalProductRepository, OrganisationRepository organisationRepository) {
        this.portalProductRepository = portalProductRepository;
        this.dataOwnerPortalProductRepository = dataOwnerPortalProductRepository;
        this.organisationRepository = organisationRepository;
    }

    public void deleteDisabledOrgs() {
        List<DataOwnerPortalProduct> list = dataOwnerPortalProductRepository.getLicensesForDisabledOrgs();
        LogManager.getLogger().info("Number disabled org licenses " + list.size());
        deleteList(list);
    }

    public void deleteDuplicates() {
        int slett = 0;
        List<DataOwnerPortalProduct> list = dataOwnerPortalProductRepository.getLicenseDuplicates();
        HashSet<Integer> okeys = getUniqueOkeys(list);
        for (Integer okey : okeys) {
            HashSet<Integer> products = getUniqueProducts(okey, list);
            for (Integer product : products) {
                List<DataOwnerPortalProduct> dopp = dataOwnerPortalProductRepository.getLicenseDuplicates(product, okey);
                dopp.remove(dopp.get(0));
                slett += dopp.size();
                deleteList(dopp);
            }
        }
        LogManager.getLogger().info("Number of duplicate licenses " + slett);
    }

    public void addLicenses(List<PortalProduct> inheritList) {
        int addedLicenses = 0;
        List<Integer> orgs = getAllOrgs();
        for (Integer okey : orgs) {
            List<DataOwnerPortalProduct> licenses = getLicenses(okey, inheritList);
            List<Organisation> childlist = findOrganisations(okey);
            for (Organisation org : childlist) {
                for (DataOwnerPortalProduct license : licenses) {
                    DataOwnerPortalProduct exisitingLicense = getLicense(license.getProductId(), org.getId());
                    if (exisitingLicense == null) {
                        DataOwnerPortalProduct dopp = new DataOwnerPortalProduct();
                        dopp.setProductId(license.getProductId());
                        dopp.setOkey(org.getId());
                        dopp.setOdkey(0);
                        saveLicense(dopp);
                        addedLicenses++;
                    }
                }
            }
        }
        LogManager.getLogger().info("New licenses " + addedLicenses);
    }

    private HashSet<Integer> getUniqueOkeys(List<DataOwnerPortalProduct> list) {
        HashSet<Integer> ret = new HashSet<Integer>();
        for (DataOwnerPortalProduct p : list) {
            ret.add(p.getOkey());
        }
        return ret;
    }

    private HashSet<Integer> getUniqueProducts(int okey, List<DataOwnerPortalProduct> list) {
        HashSet<Integer> ret = new HashSet<Integer>();
        for (DataOwnerPortalProduct p : list) {
            if (okey == p.getOkey())
                ret.add(p.getProductId());
        }
        return ret;
    }

    private void deleteList(List<DataOwnerPortalProduct> list) {
        for (DataOwnerPortalProduct p : list) {
            dataOwnerPortalProductRepository.delete(p);
        }
    }

    private List<Integer> getAllOrgs() {
        return dataOwnerPortalProductRepository.getOrgs();
    }

    private List<DataOwnerPortalProduct> getLicenses(int okey, List<PortalProduct> inheritList) {
        List<DataOwnerPortalProduct> retList = new ArrayList<>();
        List<DataOwnerPortalProduct> list = dataOwnerPortalProductRepository.findDataOwnerPortalProductByOkey(okey);
        for (DataOwnerPortalProduct item : list) {
            PortalProduct pp = getPortalProduct(inheritList, item);
            if (pp != null)
                retList.add(item);
        }
        return retList;
    }

    private PortalProduct getPortalProduct(List<PortalProduct> list, DataOwnerPortalProduct item) {
        for (PortalProduct pp : list) {
            if (pp.getProductId() == item.getProductId())
                return pp;
        }
        return null;
    }

    public List<PortalProduct> getLicenses() {
        return portalProductRepository.getInheritLicenses();
    }


    private DataOwnerPortalProduct getLicense(int produktId, int okey) {
        Optional<DataOwnerPortalProduct> license = dataOwnerPortalProductRepository.getDataOwnerPortalProductByProductIdAndOkey(produktId, okey);
        return license.orElse(null);
    }

    private DataOwnerPortalProduct saveLicense(DataOwnerPortalProduct license) {
        return dataOwnerPortalProductRepository.saveAndFlush(license);
    }

    private List<Organisation> findOrganisations(int okey) {
        List<Organisation> organisations = new ArrayList<>();
        Optional<Organisation> org = organisationRepository.findOrganisationById(okey);
        Organisation organisation = org.orElse(null);
        if (organisation == null)
            return organisations;
        organisations.add(org.get());
        organisations.addAll(findChildOrganisations(organisations));
        organisations.remove(0);
        return organisations;
    }

    private List<Organisation> findChildOrganisations(List<Organisation> inputOrganisations) {
        List<Organisation> childOrganisations = new ArrayList<>();
        for (Organisation org : inputOrganisations) {
            List<Organisation> tempOrganisations = findOrganisationsByOkeyParent(org.getId());
            if (!tempOrganisations.isEmpty()) {
                tempOrganisations.addAll(findChildOrganisations(tempOrganisations));
                childOrganisations.addAll(tempOrganisations);
            }
        }
        return childOrganisations;
    }

    private List<Organisation> findOrganisationsByOkeyParent(int okey) {
        List<Organisation> allOrgs = organisationRepository.findOrganisationsByOkeyParent(okey);
        List<Organisation> orgs = new ArrayList<>();
        for (Organisation org : allOrgs) {
            if (org.getOrgStatus() == 2 || org.getOrgStatus() ==5)
                continue;
            orgs.add(org);
        }
        return orgs;
    }
}
