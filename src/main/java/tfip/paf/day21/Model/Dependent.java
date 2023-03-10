package tfip.paf.day21.Model;

import java.sql.Date;

public class Dependent {
    private Integer id;
    private String fullName;
    private String relationship;
    private Date birthDate;
    private Employee employee;

    public Dependent() {
    }
    public Dependent(Integer id, String fullName, String relationship, Date birthDate, Employee employee) {
        this.id = id;
        this.fullName = fullName;
        this.relationship = relationship;
        this.birthDate = birthDate;
        this.employee = employee;
    }
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getRelationship() {
        return relationship;
    }
    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
    public Date getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    public Employee getEmployee() {
        return employee;
    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    
}
