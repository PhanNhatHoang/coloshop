package com.web.coloshop.Controller;

import com.web.coloshop.Repository.CategoryRepository;
import com.web.coloshop.model.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public String listCategories(Model model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "Admin/Category";
    }
    @PostMapping("/add")
    public String addCategory(@RequestParam String name) {
        Category newCategory = new Category();
        newCategory.setName(name);
        categoryRepository.save(newCategory);
        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String showEditCategoryForm(@PathVariable Long id, Model model) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + id));
        model.addAttribute("category", category);
        return "category/edit";
    }

        @GetMapping("/edit/{id}")
    public String editCategory(@PathVariable Long id, @RequestParam String name) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + id));

        category.setName(name);
        categoryRepository.save(category);

        return "redirect:/categories";
    }
}
