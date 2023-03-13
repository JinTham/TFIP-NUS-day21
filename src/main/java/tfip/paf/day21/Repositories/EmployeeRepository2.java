// package tfip.paf.day21.Repositories;

// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.stream.Collectors;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.dao.DataAccessException;
// import org.springframework.jdbc.core.JdbcTemplate;
// import org.springframework.jdbc.core.PreparedStatementCallback;
// import org.springframework.jdbc.core.PreparedStatementSetter;
// import org.springframework.jdbc.core.ResultSetExtractor;
// import org.springframework.lang.Nullable;
// import org.springframework.stereotype.Repository;

// import tfip.paf.day21.Model.Dependent;
// import tfip.paf.day21.Model.Employee;

// @Repository
// public class EmployeeRepository2 {

//     @Autowired
//     JdbcTemplate jdbcTemplate;

//     String findAllSQL = "select e.id as emp_id, e.first_name, e.last_name, e.salary, " +
//             " d.id dep_id, d.full_name, d.birthdate, d.relationship " +
//             " from employee e " +
//             " inner join dependent d " +
//             " on e.id = d.employee_id ";

//     String findByIdSQL = "select e.id as emp_id, e.first_name, e.last_name, e.salary, " +
//             " d.id dep_id, d.full_name, d.birthdate, d.relationship " +
//             " from employee e " +
//             " inner join dependent d " +
//             " on e.id = d.employee_id " +
//             " where e.id = ? ";

//     String insertSQL = "insert into employee (first_name, last_name, salary) values (?, ?, ?)";

//     String updateSQL = "update employee set first_name = ?, last_name = ?, salary = ? where id = ? ";

//     String deleteSQL = "delete from employee where id = ?";

//     public Boolean save(Employee employee) {
//         Boolean bSaved = false;

//         PreparedStatementCallback<Boolean> psc = new PreparedStatementCallback<Boolean>() {

//             @Override
//             @Nullable
//             public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//                 ps.setString(1, employee.getFirstName());
//                 ps.setString(2, employee.getLastName());
//                 ps.setFloat(3, employee.getSalary());
//                 Boolean rslt = ps.execute();
//                 return rslt;
//             }

//         };

//         bSaved = jdbcTemplate.execute(insertSQL, psc);

//         return bSaved;
//     }

//     public int update(Employee employee) {
//         int iUpdated = 0;

//         PreparedStatementSetter pss = new PreparedStatementSetter() {

//             // "update employee set first_name = 1, last_name = 2, salary = 3 where id = 4 "
//             @Override
//             public void setValues(PreparedStatement ps) throws SQLException {
//                 ps.setString(1, employee.getFirstName());
//                 ps.setString(2, employee.getLastName());
//                 ps.setFloat(3, employee.getSalary());
//                 ps.setInt(4, employee.getId());
//             }
//         };

//         iUpdated = jdbcTemplate.update(updateSQL, pss);

//         return iUpdated;
//     }

//     public int delete(Integer id) {
//         int bDeleted = 0;

//         PreparedStatementSetter pss = new PreparedStatementSetter() {
//             @Override
//             public void setValues(PreparedStatement ps) throws SQLException {

//                 ps.setInt(1, id);
//             }
//         };

//         bDeleted = jdbcTemplate.update(deleteSQL, pss);

//         return bDeleted;
//     }

//     public List<Employee> findAll() {
//         List<Employee> employees = new ArrayList<Employee>();

//         employees = jdbcTemplate.query(findAllSQL, new ResultSetExtractor<List<Employee>>() {

//             @Override
//             public List<Employee> extractData(ResultSet rs) throws SQLException, DataAccessException {
//                 List<Employee> emps = new ArrayList<Employee>();

//                 while(rs.next()) {
//                     // e.id as emp_id, e.first_name, e.last_name, e.salary,
//                     Employee employee = new Employee();
//                     employee.setId(rs.getInt("emp_id"));
//                     employee.setFirstName(rs.getString("first_name"));
//                     employee.setLastName(rs.getString("last_name"));
//                     employee.setSalary(rs.getFloat("salary"));
//                     employee.setDependents(new ArrayList<Dependent>());

//                     // d.id dep_id, d.full_name, d.birth_date, d.relationship
//                     Dependent dependant = new Dependent();
//                     dependant.setId(rs.getInt("dep_id"));
//                     dependant.setFullName(rs.getString("full_name"));
//                     dependant.setRelationship(rs.getString("relationship"));
//                     dependant.setBirthDate(rs.getDate("birthdate"));

//                     if (emps.size() == 0) {
//                         employee.getDependents().add(dependant);
//                         emps.add(employee);
//                     } else {
//                         List<Employee> tmpList = emps.stream().filter(e -> e.getId() == employee.getId()).collect(Collectors.toList());

//                         if (tmpList.size() > 0) {
//                             // append to dependant list for the found employee
//                             int idx = emps.indexOf(tmpList.get(0));

//                             if (idx >= 0) {
//                                 emps.get(idx).getDependents().add(dependant);
//                             }
//                         } else {
//                             // if the employee record in not found in the list of employees
//                             // add a new employee record together with the dependant information
//                             employee.getDependents().add(dependant);
//                             emps.add(employee);
//                         }
//                     }
//                 }

//                 return emps;
//             }
//         });

//         return employees;
//     }

//     public Employee findByEmployeeId(Integer employeeId) {
//         Employee employee = new Employee();

//         employee = jdbcTemplate.query(findByIdSQL, new ResultSetExtractor<Employee>() {

//             @Override
//             public Employee extractData(ResultSet rs) throws SQLException, DataAccessException {
//                 Employee emp = new Employee();

//                 while(rs.next()) {
//                     // e.id as emp_id, e.first_name, e.last_name, e.salary,
//                     Employee employee = new Employee();
//                     employee.setId(rs.getInt("emp_id"));
//                     employee.setFirstName(rs.getString("first_name"));
//                     employee.setLastName(rs.getString("last_name"));
//                     employee.setSalary(rs.getFloat("salary"));
//                     employee.setDependents(new ArrayList<Dependent>());

//                     // d.id dep_id, d.full_name, d.birth_date, d.relationship
//                     Dependent dependant = new Dependent();
//                     dependant.setId(rs.getInt("dep_id"));
//                     dependant.setFullName(rs.getString("full_name"));
//                     dependant.setRelationship(rs.getString("relationship"));
//                     dependant.setBirthDate(rs.getDate("birthdate"));

//                     if (rs.isFirst()) {
//                         emp = employee;
//                         emp.getDependents().add(dependant);
//                     } else {
//                         emp.getDependents().add(dependant);
//                     }

//                 }

//                 return emp;
//             }
//         }, employeeId);

//         return employee;
//     }

// }
