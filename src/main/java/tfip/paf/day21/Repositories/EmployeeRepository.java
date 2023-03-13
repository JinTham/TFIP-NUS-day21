package tfip.paf.day21.Repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import tfip.paf.day21.Model.Dependent;
import tfip.paf.day21.Model.Employee;

@Repository
public class EmployeeRepository {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    String findAllSQL = "select emp.id as emp_id,first_name,last_name,salary,dep.id as dep_id,full_name as dep_name,relationship,birthdate as dep_birthdate"
                        + " from employee as emp"
                        + " inner join dependent as dep"
                        + " on emp.id = dep.employee_id"
                        + " order by emp_id";

    String findByIdSQL = "select emp.id as emp_id,first_name,last_name,salary,dep.id as dep_id,full_name as dep_name,relationship,birthdate as dep_birthdate"
                        + " from employee as emp"
                        + " inner join dependent as dep"
                        + " on emp.id = dep.employee_id"
                        + " where emp_id = ?";

    String insertSQL = "insert into employee (first_name, last_name, salary) values (?, ?, ?);";

    String updateSQL = "update employee set first_name=?, last_name=?, salary=? where id=?";

    String deleteSQL = "delete from employee where id = ?";

    public Boolean save(Employee employee) {
        PreparedStatementCallback<Boolean> psc = new PreparedStatementCallback<Boolean>() {
            @Override
            @Nullable
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException{
                ps.setString(1,employee.getFirstName());
                ps.setString(2,employee.getLastName());
                ps.setFloat(3,employee.getSalary());
                Boolean rslt = ps.execute();
                return rslt;
            }
        };
        return jdbcTemplate.execute(insertSQL, psc);
    }

    public int update(Employee employee) {
        int updated = 0;
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1,employee.getFirstName());
                ps.setString(2,employee.getLastName());
                ps.setFloat(3,employee.getSalary());
                ps.setInt(4,employee.getId());
            }
        };
        updated = jdbcTemplate.update(updateSQL, pss);
        return updated;
    }

    public int delete(Employee employee) {
        int deleted = 0;
        // PreparedStatementSetter pss = new PreparedStatementSetter() {
        //     @Override
        //     public void setValues(PreparedStatement ps) throws SQLException {
        //         ps.setInt(1,employee.getId());
        //     }
        // };
        // deleted = jdbcTemplate.update(deleteSQL, pss);
        deleted = jdbcTemplate.update(deleteSQL,employee.getId());
        return deleted;
    }

    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<Employee>();
        employees = jdbcTemplate.query(findAllSQL, new ResultSetExtractor<List<Employee>>() {
            @Override
            public List<Employee> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Employee> emps = new ArrayList<>();
                while (rs.next()) {
                    Employee employee = new Employee();
                    employee.setId(rs.getInt("emp_id"));
                    employee.setFirstName(rs.getString("first_name"));
                    employee.setLastName(rs.getString("last_name"));
                    employee.setSalary(rs.getFloat("salary"));

                    Dependent dependent = new Dependent();
                    dependent.setId(rs.getInt("dep_id"));
                    dependent.setFullName(rs.getString("dep_name"));
                    dependent.setRelationship(rs.getString("relationship"));
                    dependent.setBirthDate(rs.getDate("dep_birthdate"));
                    dependent.setEmployee(employee);

                    if (emps.size() == 0) { //if emps list is empty
                        employee.getDependents().add(dependent);
                        emps.add(employee);
                    } else { //if emps list is not empty
                        // check if emps already have record of current employee, and pass the record into tmpList
                        List<Employee> tmpList = emps.stream().filter(e->e.getId() == employee.getId()).collect(Collectors.toList());
                        // if tmpList is not empty, means emps already have record of current employee
                        if (tmpList.size() > 0) {
                            // get the index of current employee inside the emps list
                            int idx = emps.indexOf(tmpList.get(0));
                            if (idx >= 0) { // if the index exists
                                // append current dependent to the dependent list of the current employee
                                emps.get(idx).getDependents().add(dependent);
                            }
                        } else {
                            // if the employee record is not found in the list of employees, add a new employee record together with the dependent info
                            employee.getDependents().add(dependent);
                            emps.add(employee);
                        }
                    }
                }   
                return emps;
            }
        });
        return employees;
    }

    public Employee findById(Integer id) {
        Employee employee = jdbcTemplate.query(findByIdSQL, new ResultSetExtractor<Employee>() {
            @Override
            public Employee extractData(ResultSet rs) throws SQLException, DataAccessException {
                Employee employee = new Employee();
                employee.setId(rs.getInt("emp_id"));
                employee.setFirstName(rs.getString("first_name"));
                employee.setLastName(rs.getString("last_name"));
                employee.setSalary(rs.getFloat("salary"));

                while (rs.next()) {
                    Dependent dependent = new Dependent();
                    dependent.setId(rs.getInt("dep_id"));
                    dependent.setFullName(rs.getString("dep_name"));
                    dependent.setRelationship(rs.getString("relationship"));
                    dependent.setBirthDate(rs.getDate("dep_birthdate"));

                    employee.getDependents().add(dependent);
                }
                return employee;
            }
        }, id);
        return employee;
    }

}
