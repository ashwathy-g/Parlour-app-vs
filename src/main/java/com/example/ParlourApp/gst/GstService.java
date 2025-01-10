package com.example.ParlourApp.gst;

import com.example.ParlourApp.category.CategoryRegModel;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class GstService {
    
    @Autowired
    GstRepository gstRepository;
    public GstData saveGst(GstData gstData) 
    {
        return gstRepository.save(gstData);
    }

    public Optional<GstData> updategst(Long id, GstData gstData)
    {
        Optional<GstData>existingGst=gstRepository.findById(id);
        if (existingGst.isPresent())
        {
            GstData gstData1=existingGst.get();
            if (gstData.getItem() != null) {
                gstData1.setItem(gstData.getItem());
            }
            if (gstData.getAmount() != 0) {
                gstData1.setAmount(gstData.getAmount());
            }
            gstRepository.save(gstData1);
            return Optional.of(gstData1);


        }else {
            return Optional.empty();
        }
    }
    public  void deleteGst(Long Id)
    {
        if (!gstRepository.existsById(Id))
        {
            throw new EntityNotFoundException("Gst with ID" + Id + "not found .");
        }
        gstRepository.deleteById(Id);
    }
    public List<GstData> getAllGst() {
        return gstRepository.findAll();
    }
}
