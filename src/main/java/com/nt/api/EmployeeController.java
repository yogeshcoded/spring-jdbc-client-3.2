package com.nt.api;

import com.nt.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;



@RestController
@RequiredArgsConstructor
public class EmployeeController {
    private final JdbcClient jdbcClient;

    public static final String INSERT = "INSERT INTO Employee(id,name,sal) VALUES (?,?)";


    @PostMapping("/add")
    public String saveEmployee(@RequestBody Employee emp) {
        int count = jdbcClient.sql(INSERT)
                .params(List.of(emp.name(),emp.sal()))
                .update();
        return count==1?"employee save successfully":"problem to save employee";
    }

    @GetMapping("/all")
    public List<Employee> getAllEmployee() {
        return jdbcClient.sql("SELECT id,name,sal FROM employee")
                .query(Employee.class)
                .list();
    }

    @GetMapping("/{id}")
    public Optional<Employee> getEmployeeById(@PathVariable int id) {
        return jdbcClient.sql("SELECT id,name,sal FROM employee where id=:id")
                .param("id", id)
                .query(Employee.class)
                .optional();


    }


}
