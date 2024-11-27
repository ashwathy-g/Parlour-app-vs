package com.example.ParlourApp.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/Categories")
public class CategoryController
{
    @Autowired
    CategoryService categoryService;

@PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/add")

    public ResponseEntity<CategoryRegModel> addCategory(@RequestParam ("name")String name,
                                                        @RequestParam("image") MultipartFile image) {
        CategoryRegModel categoryRegModel=new CategoryRegModel();
        categoryRegModel.setName(name);
        CategoryRegModel addedCategory = categoryService.addCategory(categoryRegModel,image);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedCategory);
    }
 @GetMapping("/{id}")
 public ResponseEntity<CategoryRegModel>getCategoryById_id(@PathVariable Long id)
 {
     Optional<CategoryRegModel>categoryRegModelOptional=categoryService.getCategoryById_id(id);
     if (categoryRegModelOptional.isPresent())
     {
         return new ResponseEntity<>(categoryRegModelOptional.get(),HttpStatus.OK);

     }else {
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
     }

 }
    @GetMapping("/all")
    public ResponseEntity<List<CategoryRegModel>> getAllCategories() {
        List<CategoryRegModel> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
}
