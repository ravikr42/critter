package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class PetService {

    private PetRepository petRepository;
    private CustomerRepository customerRepository;

    public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    public List<Pet> getAllPetsByPetIds(List<Long> ids) {
        return petRepository.findAllById(ids);
    }

    public List<Pet> findAllPets() {
        return petRepository.findAll();
    }


    public Pet addPet(Pet pet) {
        petRepository.save(pet);
        Customer petCustomer = pet.getCustomer();

        if (petCustomer != null) {
            Long customerId = petCustomer.getId();
            Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
            if (optionalCustomer.isPresent()) {
                Customer customer = optionalCustomer.get();
                if (customer.getPets() != null) {
                    customer.getPets().add(pet);
                }
                customerRepository.save(customer);
            }
        } else {
            throw new RuntimeException("Customer Not Found");
        }

        return pet;
    }


    public Pet findPetById(Long id) {
        Optional<Pet> optionalPet = petRepository.findById(id);
        if (optionalPet.isPresent()) {
            return optionalPet.get();
        } else {
            throw new RuntimeException("Pet is not found");
        }
    }

    public List<Pet> getAllPetsByCustomerId(long customerId) {
        List<Pet> pets;
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            pets = optionalCustomer.get().getPets();
        } else {
            pets = new ArrayList<>();
        }
        return pets;
    }
}
