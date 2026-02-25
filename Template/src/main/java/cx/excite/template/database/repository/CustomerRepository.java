package cx.excite.template.database.repository;

import cx.excite.template.database.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findCustomerByCustomerTypeAndExtraInfo(Long customerType, String extraInfo);
    Optional<Customer> findCustomerByPrivEmailAndCustomerType(String email, Long customerType);
    Optional<Customer> findCustomerByExtCuCodeAndCustomerType(String extCuCode, Long customerType);
    Optional<Customer> findTopByExtCuCodeAndCustomerType(String extCuCode, Long customerType);
}