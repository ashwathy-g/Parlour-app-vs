package com.example.ParlourApp.category;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CategoryService
{
    @Autowired
    CategoryRepository categoryRepository;
    public CategoryRegModel addCategory(CategoryRegModel categoryRegModel, MultipartFile image) {
        try {
if (image!=null&&!image.isEmpty())
{
    categoryRegModel.setImage(image.getBytes());
}else {
    categoryRegModel.setImage(null);
}



        }catch (Exception e){
            log.error("Error saving image",e);
        }
        return categoryRepository.save(categoryRegModel);
    }



    public List<CategoryRegModel> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<CategoryRegModel>getCategoryById_id(Long id)
    {
        return categoryRepository.findById(id);
    }
    public CategoryRegModel getCategoryById(Long categoryId) {

        return  categoryRepository.findById(categoryId).orElse(null);
    }

}
