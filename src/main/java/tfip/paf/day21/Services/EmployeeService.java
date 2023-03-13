package tfip.paf.day21.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfip.paf.day21.Model.Employee;
import tfip.paf.day21.Repositories.EmployeeRepository;

@Service
public class EmployeeService {
    
    @Autowired
    EmployeeRepository employeeRepo;

    public Boolean save (Employee employee) {
        return employeeRepo.save(employee);
    }

    public int update (Employee employee) {
        return employeeRepo.update(employee);
    }

    public int delete (Integer id) {
        return employeeRepo.delete(id);
    }

    public List<Employee> findAll() {
        return employeeRepo.findAll();
    }

    public Employee findByEmployeeId (Integer employeeId) {
        return employeeRepo.findByEmployeeId(employeeId);
    }
}
