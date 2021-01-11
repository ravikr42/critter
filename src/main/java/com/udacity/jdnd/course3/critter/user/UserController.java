package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.services.EmployeeService;
import com.udacity.jdnd.course3.critter.services.PetService;
import com.udacity.jdnd.course3.critter.util.DTOEntityConverter;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.udacity.jdnd.course3.critter.util.DTOEntityConverter.getCustomerDTO;
import static com.udacity.jdnd.course3.critter.util.DTOEntityConverter.getEmployeeDTO;

/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private CustomerService customerService;
    private PetService petService;
    private EmployeeService empService;

    public UserController(CustomerService customerService, PetService petService, EmployeeService empService) {
        this.customerService = customerService;
        this.petService = petService;
        this.empService = empService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setNotes(customerDTO.getNotes());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());

        List<Long> customerPetIds = customerDTO.getPetIds();
        if (customerPetIds != null) {
            List<Pet> pets = petService.getAllPetsByPetIds(customerPetIds);
            customer.setPets(pets);
        }
        Customer saved = customerService.addCustomer(customer);
        customerDTO.setId(saved.getId());
        return customerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        List<CustomerDTO> customerDTOs = new ArrayList<>();

        for (Customer customer : customers) {
            customerDTOs.add(getCustomerDTO(customer));
        }
        return customerDTOs;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        Pet pet = petService.findPetById(petId);
        Customer customer = pet.getCustomer();
        return getCustomerDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        employee.setSkills(employeeDTO.getSkills());
        Employee savedEmployee = empService.saveEmp(employee);
        employeeDTO.setId(savedEmployee.getId());
        return employeeDTO;
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = empService.getEmployeeById(employeeId);
        return getEmployeeDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Employee employee = empService.getEmployeeById(employeeId);
        employee.setDaysAvailable(daysAvailable);
        empService.saveEmp(employee);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employees = empService.getAvailableEmployees(employeeDTO.getSkills(),
                employeeDTO.getDate().getDayOfWeek());
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        for (Employee employee : employees) {
            employeeDTOs.add(getEmployeeDTO(employee));
        }
        return employeeDTOs;
    }

}
