package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Transactional
@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee saveEmp(final Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            return optionalEmployee.get();
        } else {
            throw new RuntimeException("Employee is not Found");
        }
    }


    public List<Employee> getAvailableEmployees(Set<EmployeeSkill> empSkills, DayOfWeek dayOfWeek) {

        List<Employee> employees = employeeRepository.findAllByDaysAvailableContaining(dayOfWeek);
        List<Employee> availableEmployees = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getSkills().containsAll(empSkills)) {
                availableEmployees.add(employee);
            }
        }
        return availableEmployees;
    }


}
