package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class PetService {

    private PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public List<Pet> getAllPetsByPetIds(List<Long> ids) {
        return petRepository.findAllById(ids);
    }

    public List<Pet> findAllPets() {
        return petRepository.findAll();
    }

    public Pet addPet(Pet pet) {

        return null;
    }


}
