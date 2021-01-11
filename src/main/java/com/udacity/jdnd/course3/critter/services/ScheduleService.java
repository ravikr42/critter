package com.udacity.jdnd.course3.critter.services;


import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ScheduleService {

    private ScheduleRepository scheduleRepository;
    private CustomerRepository customerRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, CustomerRepository customerRepository) {
        this.scheduleRepository = scheduleRepository;
        this.customerRepository = customerRepository;
    }

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getSchedulesForPet(long petId) {
        return scheduleRepository.findAllByPetsId(petId);
    }

    public List<Schedule> getSchedulesForEmployee(long empId) {
        return scheduleRepository.findAllByEmployeesId(empId);
    }

    public List<Schedule> getSchedulesForCustomer(long customerId) {
        Customer customer;
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);

        if (optionalCustomer.isPresent()) {
            customer = optionalCustomer.get();
        } else {
            throw new RuntimeException("Customer not found with Id: " + customerId);
        }

        List<Pet> pets = customer.getPets();

        List<Schedule> schedules = new ArrayList<Schedule>();
        for (Pet pet : pets) {
            schedules.addAll(scheduleRepository.findAllByPetsId(pet.getId()));
        }
        return schedules;
    }
}
