package com.example.ParlourApp.parlour;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OtpService
{
    @Autowired
    ParlourRepository parlourRepository;


    private final Cache<String,String> otpCache= CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES).build();
    public  String generateOtp(String email) {
        String otp = String.format("%06d",new Random().nextInt(999999));
        Optional<ParlourRegModel> optionalParlourRegModel = parlourRepository.findByEmail(email);
        if (optionalParlourRegModel.isPresent()) {

            otpCache.put(email, otp);
        } else {
            throw new IllegalArgumentException("Email not found in the system");
        }
        return otp;
    }
    public boolean validateOtp(String email,String inputOtp)
    {
        String cachedOtp=otpCache.getIfPresent(email);
        return cachedOtp!=null && cachedOtp.equals(inputOtp);
    }
    public void clearOtp(String email)
    {
        otpCache.invalidate(email);
    }
}



