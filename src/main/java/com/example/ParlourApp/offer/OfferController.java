package com.example.ParlourApp.offer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/offer")
public class OfferController
{
    @Autowired
    OfferService offerService;
    @Autowired
    OfferRepository offerRepository;
   @PostMapping("/add")
public ResponseEntity<OfferDetails>addOffer(@RequestParam("typeId") Long typeId,
                                            @RequestParam("image")MultipartFile image,
                                            @RequestParam("offerName")String offerName,
                                            @RequestParam("discount")Double discount,
                                            @RequestParam("startDate")LocalDate startDate,
                                            @RequestParam("endDate")LocalDate endDate,
                                            @RequestParam("parlourId")Long parlourId)
   {
       OfferDetails offerDetails=new OfferDetails();
       offerDetails.setTypeId(typeId);
       offerDetails.setOfferName(offerName);
       offerDetails.setDiscount(discount);
       offerDetails.setStartDate(startDate);
       offerDetails.setEndDate(endDate);
       offerDetails.setParlourId(parlourId);

       try {
           offerDetails.setImage(image.getBytes());
       }catch (IOException e)
       {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }
       OfferDetails createdOffer=offerService.addOffer(offerDetails);
       return ResponseEntity.ok(createdOffer);

   }

   @GetMapping("/offerId")
    public ResponseEntity<Map<String,Object>>getOfferWithDiscountDetails(@RequestParam Long offerId)
   {
       Map<String,Object>response=offerService.getOfferWithDiscountDetails(offerId);
       return ResponseEntity.ok(response);
   }
   @GetMapping("/getAllOffers")
   public ResponseEntity<List<OfferDetails>>getAllOffers()
   {
       return new ResponseEntity<>(offerService.getAllOffers(),HttpStatus.OK);
   }
   @GetMapping("/getByStartDate")
   public ResponseEntity<List<OfferDetails>>getOffersByStartDate(@RequestParam("startDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate startDate)
   {
       return new ResponseEntity<>(offerService.getOffersByStartDate(startDate),HttpStatus.OK);
   }
    @GetMapping("/getByEndDate")
    public ResponseEntity<List<OfferDetails>> getOffersByEndDate(@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return new ResponseEntity<>(offerService.getOffersByEndDate(endDate), HttpStatus.OK);
    }
   @GetMapping("/getByDateRange")
   public ResponseEntity<List<OfferDetails>> getOffersByDateRange (@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                   @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
       List<OfferDetails> offers = offerService.getOffersByDateRange(startDate, endDate);
       return new ResponseEntity<>(offers, HttpStatus.OK);
   }

   @PutMapping("/update")
    public ResponseEntity<OfferDetails>updateOffer(@RequestParam Long offerId,
                                                   @RequestParam("typeId") Long typeId,
                                                   @RequestParam("image")MultipartFile image,
                                                   @RequestParam("offerName")String offerName,
                                                   @RequestParam("discount")Double discount,
                                                   @RequestParam("startDate")LocalDate startDate,
                                                   @RequestParam("endDate")LocalDate endDate,
                                                   @RequestParam("parlourId")Long parlourId)
   {
       Optional<OfferDetails> updatedOffer=offerService.updatedOffer(offerId,typeId,image,offerName,discount,startDate,endDate,parlourId);
       return updatedOffer.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
   }
   @DeleteMapping("/delete")
    public ResponseEntity<String>deleteOffer(@RequestParam Long offerId)
   {
       boolean deleted=offerService.deleteOffer(offerId);
       if (deleted)
       {
           return ResponseEntity.ok("Offer deleted successfully");
       }else {
           return ResponseEntity.notFound().build();
       }
   }
}




