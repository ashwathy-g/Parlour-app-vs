package com.example.ParlourApp.appcharge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/appCharge")

public class ChargeController
{
    @Autowired
    ChargeService chargeService;
    @PostMapping("/addCharge")
    public ResponseEntity<?>addCharge(@RequestBody ChargeDetails chargeDetails)
    {
        try{
            ChargeDetails chargeDetails1=chargeService.chargeDetails1(chargeDetails);
            return ResponseEntity.ok(chargeDetails1);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(500).body("Error adding charge :" +e.getMessage());
        }

    }
    @PutMapping("/updateCharge")
    public ResponseEntity<ChargeDetails>updateCharge(@RequestParam Long Id,@RequestBody ChargeDetails chargeDetails)
    {
        Optional<ChargeDetails> updatedCharge=chargeService.updateCharge(Id,chargeDetails);
        if (updatedCharge.isPresent())
        {
            return ResponseEntity.ok(updatedCharge.get());
        }else
        {
            return ResponseEntity.notFound().build();
        }

    }
    @DeleteMapping("/deleteCharge")
    public ResponseEntity<String>deleteCharge(@RequestParam Long Id)
    {
        chargeService.deleteCharge(Id);
        return ResponseEntity.ok("App charge Deleted Successfully.");
    }
    @GetMapping("/allCharge")
    public ResponseEntity<List<ChargeDetails>>getAllAppCharge()
    {
        List<ChargeDetails> chargeDetails=chargeService.getAllAppCharge();
        return ResponseEntity.ok(chargeDetails);
    }
}
