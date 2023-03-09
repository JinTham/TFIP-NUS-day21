package tfip.paf.day21.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tfip.paf.day21.Model.Customer;
import tfip.paf.day21.Services.CustomerService;

@RestController
@RequestMapping(path="/api/customers")
public class CustomerController {
    
    @Autowired
    CustomerService customerSvc;

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerSvc.retrieveAllCustomers();
    }

    @GetMapping(path="/limit")
    public List<Customer> getAllCustomers(@RequestParam("limit") int limit, @RequestParam("offset") int offset) {
        return customerSvc.retrieveAllCustomersWithLimitOffset(limit,offset);
    }

    @GetMapping(path="/{id}")
    public Customer getCustomerById(@PathVariable("id") int id) {
        return customerSvc.retrieveCustomerById(id);
    }
}
