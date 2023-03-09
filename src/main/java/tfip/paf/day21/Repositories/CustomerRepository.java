package tfip.paf.day21.Repositories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import tfip.paf.day21.Model.Customer;

@Repository
public class CustomerRepository {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String findAllSQL = "select * from customer";

    public List<Customer> getAllCustomers() {
        final List<Customer> customerList = new ArrayList<>();
        final SqlRowSet rs = jdbcTemplate.queryForRowSet(findAllSQL);
        
        while (rs.next()) {
            Customer customer = new Customer();
            customer.setId(rs.getInt("id"));
            customer.setFirstName(rs.getString("first_name"));
            customer.setLastName(rs.getString("last_name"));
            customer.setDob(rs.getDate("dob"));
            customerList.add(customer);
        }
        return Collections.unmodifiableList(customerList);
    }
}
