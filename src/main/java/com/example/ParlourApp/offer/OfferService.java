package com.example.ParlourApp.offer;

import com.example.ParlourApp.discount.DiscountDetails;
import com.example.ParlourApp.discount.DiscountRepository;
import com.example.ParlourApp.parlour.ParlourRegModel;
import com.example.ParlourApp.parlour.ParlourRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class OfferService {


    @Autowired
    OfferRepository offerRepository;
    @Autowired
    DiscountRepository discountRepository;
    @Autowired
    ParlourRepository parlourRepository;
    public OfferDetails addOffer(OfferDetails offerDetails)
    {
        return offerRepository.save(offerDetails);
    }

    public Map<String, Object> getOfferWithDiscountDetails(Long offerId)
    {
        OfferDetails offerDetails=offerRepository.findById(offerId)
                .orElseThrow(()->new RuntimeException("Offer not found"));
        Long typeId= offerDetails.getTypeId();
        DiscountDetails discountDetails=discountRepository.findById(typeId)
                .orElseThrow(()->new RuntimeException("Discount type not found"));
        Long parlourId = offerDetails.getParlourId();
        ParlourRegModel parlourRegModel = parlourRepository.findById(parlourId)
                .orElseThrow(() -> new RuntimeException("Parlour not found"));
        Map<String, Object> response = new HashMap<>();
        response.put("offerName", offerDetails.getOfferName());
        response.put("discountType", discountDetails.getDiscountType());
        response.put("discount",offerDetails.getDiscount());
        response.put("startDate", offerDetails.getStartDate());
        response.put("endDate", offerDetails.getEndDate());
        response.put("typeId", typeId);
        response.put("parlourId", parlourId);
        response.put("parlourName", parlourRegModel.getParlourName());
        return response;
    }
    public List<OfferDetails> getAllOffers() {
        return offerRepository.findAll();
    }
    public Optional<OfferDetails> updatedOffer(Long offerId, Long typeId, MultipartFile image, String offerName, Double discount, LocalDate startDate, LocalDate endDate, Long parlourId)
    {
        Optional<OfferDetails>optionalOfferDetails=offerRepository.findById(offerId);
        if (optionalOfferDetails.isEmpty())
        {
            return Optional.empty();
        }
        OfferDetails offerDetails1=optionalOfferDetails.get();
        offerDetails1.setTypeId(typeId);
        offerDetails1.setOfferName(offerName);
        offerDetails1.setDiscount(discount);
        offerDetails1.setStartDate(startDate);
        offerDetails1.setEndDate(endDate);
        offerDetails1.setParlourId(parlourId);
        try {
            if (image!=null&&!image.isEmpty())
            {
                offerDetails1.setImage(image.getBytes());
            }

        }catch (IOException e)
        {
            throw new RuntimeException("Error reading image file",e);

        }
        offerRepository.save(offerDetails1);
        return Optional.of(offerDetails1);
    }

    public boolean deleteOffer(Long offerId)

    {
        if (offerRepository.existsById(offerId))
        {
            offerRepository.deleteById(offerId);
            return true;
        }else {
            return false;
        }
    }

    public List<OfferDetails> getOffersByDateRange(LocalDate startDate, LocalDate endDate)
    {
        return offerRepository.findByStartDateBetween(startDate,endDate);
    }

    public List<OfferDetails> getOffersByStartDate(LocalDate startDate)
    {
        return offerRepository.findByStartDate(startDate);
    }
    public List<OfferDetails> getOffersByEndDate(LocalDate endDate) {
        return offerRepository.findByEndDate(endDate);
    }

}



