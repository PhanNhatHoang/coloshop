package com.web.coloshop.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    @GetMapping("Admin/Add_Product")
    public String Addproduct(){
        return "/Admin/addProduct";
    }
}
