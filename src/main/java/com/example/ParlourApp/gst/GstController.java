package com.example.ParlourApp.gst;

import com.example.ParlourApp.category.CategoryRegModel;
import com.example.ParlourApp.employee.EmployeeRegModel;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/gst")

public class GstController
{

    @Autowired
    GstService gstService;
  @PostMapping("/addGst")
    public ResponseEntity<?>addGst(@RequestBody GstData gstData)
  {
      try {
             GstData savedGst = gstService.saveGst(gstData);
              return ResponseEntity.ok(savedGst);

          }catch(Exception e)
          {
              return ResponseEntity.status(500).body("Error adding Gst :" + e.getMessage());
          }

      }
    @PutMapping("/updateGst")
    public ResponseEntity<GstData> updateGst(@RequestParam Long Id,@RequestBody GstData gstData)
    {
        Optional<GstData> updatedGst = gstService.updateGst(Id,gstData);
        if (updatedGst.isPresent()) {
            return ResponseEntity.ok(updatedGst.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/deleteGst")
    public ResponseEntity<String>deleteGst(@RequestParam Long Id)
    {
        gstService.deleteGst(Id);
        return ResponseEntity.ok("Gst deleted Successfully.");
    }
    @GetMapping("/allGst")
    public ResponseEntity<List<GstData>> getAllGst() {
        List<GstData> gstData = gstService.getAllGst();
        return ResponseEntity.ok(gstData);
    }
}

