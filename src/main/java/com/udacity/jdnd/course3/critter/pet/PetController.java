package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.services.PetService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.udacity.jdnd.course3.critter.util.DTOEntityConverter.getPetDTO;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private PetService petService;
    private CustomerService customerService;

    public PetController(PetService petService, CustomerService customerService) {
        this.petService = petService;
        this.customerService = customerService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = new Pet();
        Customer customer = customerService.findById(petDTO.getOwnerId());
        pet.setPetType(petDTO.getType());
        pet.setName(petDTO.getName());
        pet.setNotes(petDTO.getNotes());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setCustomer(customer);
        if (petDTO.getId() != 0) {
            pet = petService.findPetById(petDTO.getId());
        }
        Pet savedPet = petService.addPet(pet);
        petDTO.setId(savedPet.getId());
        return petDTO;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.findPetById(petId);
        return getPetDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets() {
        List<Pet> pets = petService.findAllPets();
        List<PetDTO> petDTOs = new ArrayList<>();
        for (Pet pet : pets) {
            petDTOs.add(getPetDTO(pet));
        }
        return petDTOs;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petService.getAllPetsByCustomerId(ownerId);
        List<PetDTO> petDTOs = new ArrayList<>();
        for (Pet pet : pets) {
            petDTOs.add(getPetDTO(pet));
        }
        return petDTOs;
    }
}
