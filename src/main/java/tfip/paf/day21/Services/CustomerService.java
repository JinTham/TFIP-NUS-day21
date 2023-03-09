package tfip.paf.day21.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfip.paf.day21.Model.Customer;
import tfip.paf.day21.Repositories.CustomerRepository;

@Service
public class CustomerService {
    
    @Autowired
    CustomerRepository customerRepo;

    public List<Customer> retrieveAllCustomers() {
        return customerRepo.getAllCustomers();
    }
}
