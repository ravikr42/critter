package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.services.EmployeeService;
import com.udacity.jdnd.course3.critter.services.PetService;
import com.udacity.jdnd.course3.critter.services.ScheduleService;
import com.udacity.jdnd.course3.critter.util.DTOEntityConverter;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.udacity.jdnd.course3.critter.util.DTOEntityConverter.getScheduleDTO;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private EmployeeService employeeService;
    private PetService petService;
    private ScheduleService scheduleService;


    public ScheduleController(EmployeeService employeeService, PetService petService, ScheduleService scheduleService) {
        this.employeeService = employeeService;
        this.petService = petService;
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        List<Long> employeeIds = scheduleDTO.getEmployeeIds();
        List<Long> petIds = scheduleDTO.getPetIds();
        List<Employee> employeeList = new ArrayList<>();
        List<Pet> petList = petService.getAllPetsByPetIds(petIds);
        for (Long id : employeeIds) {
            Employee employee = employeeService.getEmployeeById(id);
            if (employee != null) {
                employeeList.add(employee);
            } else {
                throw new RuntimeException("Employee is not found with id: " + id);
            }
        }
        schedule.setDate(scheduleDTO.getDate());
        schedule.setEmployees(employeeList);
        schedule.setPets(petList);
        schedule.setActivities(scheduleDTO.getActivities());

        Schedule savedSchedule = scheduleService.saveSchedule(schedule);
        scheduleDTO.setId(savedSchedule.getId());
        return scheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        for (Schedule schedule : schedules) {
            scheduleDTOs.add(getScheduleDTO(schedule));
        }
        return scheduleDTOs;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = scheduleService.getSchedulesForPet(petId);
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        for (Schedule schedule : schedules) {
            scheduleDTOs.add(getScheduleDTO(schedule));
        }
        return scheduleDTOs;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.getSchedulesForEmployee(employeeId);
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        for (Schedule schedule : schedules) {
            scheduleDTOs.add(getScheduleDTO(schedule));
        }
        return scheduleDTOs;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = scheduleService.getSchedulesForCustomer(customerId);
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        for (Schedule schedule : schedules) {
            scheduleDTOs.add(getScheduleDTO(schedule));
        }
        return scheduleDTOs;
    }
}
