package com.example.ParlourApp.appcharge;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@Slf4j
public class ChargeService {
    @Autowired
    ChargeRepository chargeRepository;

    public ChargeDetails chargeDetails1(ChargeDetails chargeDetails) {
        return chargeRepository.save(chargeDetails);
    }

    public Optional<ChargeDetails> updateCharge(Long id, ChargeDetails chargeDetails) {
        Optional<ChargeDetails> existingCharge = chargeRepository.findById(id);
        if (existingCharge.isPresent()) {

            ChargeDetails chargeDetails1 = existingCharge.get();
            chargeDetails1.setStartRange(chargeDetails.getStartRange());
            chargeDetails1.setEndRange(chargeDetails.getEndRange());
            chargeDetails1.setPercentage(chargeDetails.getPercentage());
            return Optional.of(chargeRepository.save(chargeDetails1));
        }
        return Optional.empty();
    }

    public void deleteCharge(Long Id)
    {
        if (!chargeRepository.existsById(Id))
        {
            throw new EntityNotFoundException("Charge with ID "+ Id +"Not Found .");
        }
        chargeRepository.deleteById(Id);

    }
    public List<ChargeDetails>getAllAppCharge()
    {
        return chargeRepository.findAll();
    }
}