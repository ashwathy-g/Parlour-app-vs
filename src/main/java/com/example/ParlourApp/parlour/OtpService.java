package com.example.ParlourApp.parlour;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class OtpService
{
    @Autowired
    ParlourRepository parlourRepository;


    public  String generateOtp(String email)
    {
        String otp=String.valueOf(new Random().nextInt(999999));
        Optional<ParlourRegModel> optionalParlourRegModel=parlourRepository.findByEmail(email);
        if (optionalParlourRegModel.isPresent())
        {
            ParlourRegModel parlourRegModel=optionalParlourRegModel.get();
            parlourRegModel.setOtp(otp);
            parlourRepository.save(parlourRegModel);

        }
        return otp;
    }
}
