package com.udacity.jdnd.course3.critter.util;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DTOEntityConverter {

    public static CustomerDTO getCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setNotes(customer.getNotes());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setPetIds(getPetIds(customer));
        return customerDTO;
    }

    private static List<Long> getPetIds(Customer customer) {
        List<Long> petIds = new ArrayList<Long>();
        if (customer.getPets() == null) {
            return petIds;
        } else {
            List<Pet> pets = customer.getPets();
            for (Pet p : pets) {
                petIds.add(p.getId());
            }
            return petIds;
        }
    }

    public static PetDTO getPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setName(pet.getName());
        petDTO.setNotes(pet.getNotes());
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setType(pet.getPetType());
        petDTO.setOwnerId(pet.getCustomer().getId());
        return petDTO;
    }

    public static EmployeeDTO getEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSkills(employee.getSkills());
        employeeDTO.setDaysAvailable(employee.getDaysAvailable());
        return employeeDTO;
    }

    public static ScheduleDTO getScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setActivities(schedule.getActivities());
        scheduleDTO.setPetIds(schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
        scheduleDTO.setDate(schedule.getDate());
        return scheduleDTO;
    }
}
