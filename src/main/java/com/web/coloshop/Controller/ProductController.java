package com.web.coloshop.Controller;

import com.web.coloshop.Service.BrandService;
import com.web.coloshop.Service.CategoryService;
import com.web.coloshop.Service.ProductService;
import com.web.coloshop.model.Brand;
import com.web.coloshop.model.Category;
import com.web.coloshop.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.IIOException;
import java.io.IOException;
import java.util.List;
import java.util.Base64;

@Controller
public class ProductController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public String listProduct(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "Admin/product";
    }
    @GetMapping("/Admin/Add_Product")
    public String Addproduct(Model model){

        List<Category> categories = categoryService.findAllByActivated();
        List<Brand> brands = brandService.findAllByActivated();
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);
        return "Admin/addProduct";
    }
    @PostMapping("/Admin/Save_Product")
    public String saveProduct(@ModelAttribute Product product, RedirectAttributes attributes, MultipartFile imageProduct)throws IOException {
        try{

        product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
        product.set_activated(true);
        product.set_deleted(false);
        productService.save(product);
        return "redirect:/products";
        }catch (IIOException e) {
            // Xử lý ngoại lệ IOException
            // Ví dụ: ghi log, hiển thị thông báo lỗi cho người dùng, vv.
            return "error"; // Hoặc chuyển hướng đến trang lỗi
        }
    }
    @GetMapping("/Admin/Update_Product/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model){

        List<Category> categories = categoryService.findAllByActivated();
        List<Brand> brands = brandService.findAllByActivated();
        Product product = productService.findById(id);
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);
        model.addAttribute("product", product);
        return "Admin/UpdateProduct";
    }
    @PostMapping("/Admin/Update_Product/{id}")
    public String UpdateProduct(@PathVariable Long id,
                                @ModelAttribute Product product,
                                RedirectAttributes attributes,
                                MultipartFile imageProduct)throws IOException {
        try{

            product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            product.set_activated(true);
            product.set_deleted(false);
            productService.save(product);
            return "redirect:/products";
        }catch (IIOException e) {
            // Xử lý ngoại lệ IOException
            // Ví dụ: ghi log, hiển thị thông báo lỗi cho người dùng, vv.
            return "error"; // Hoặc chuyển hướng đến trang lỗi
        }
    }
    @RequestMapping(value = "/delete-Product", method = {RequestMethod.PUT, RequestMethod.GET})
    public String delete(Long id,Model model){
        try {
            productService.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/products";
    }

    @RequestMapping(value = "/enable-Product", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enable(Long id,Model model){
        try {
            productService.enabledById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/products";
    }
}
