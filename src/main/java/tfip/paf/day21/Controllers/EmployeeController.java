package tfip.paf.day21.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tfip.paf.day21.Model.Employee;
import tfip.paf.day21.Services.EmployeeService;

@RestController
@RequestMapping(path="/api/employees")
public class EmployeeController {
    
    @Autowired
    EmployeeService employeeSvc;

    @PostMapping
    public ResponseEntity<Boolean> save(@RequestBody Employee employee) {
        Boolean saved = employeeSvc.save(employee);
        return new ResponseEntity<Boolean>(saved, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Integer> update(@RequestBody Employee employee) {
        Integer updated = employeeSvc.update(employee);
        return new ResponseEntity<Integer>(updated, HttpStatus.OK);
    }

    @DeleteMapping(path="/{employee-id}")
    public ResponseEntity<Integer> delete(@PathVariable("employee-id") Integer id) {
        Integer deleted = employeeSvc.delete(id);
        return new ResponseEntity<Integer>(deleted, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> findAll() {
        List<Employee> employees = employeeSvc.findAll();
        return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
    }

    @GetMapping(path="/{employee-id}")
    public ResponseEntity<Employee> findByEmployeeId(@PathVariable("employee-id") Integer employeeId) {
        Employee employee = employeeSvc.findByEmployeeId(employeeId);
        return new ResponseEntity<Employee>(employee, HttpStatus.OK);
    }
}
