package com.web.coloshop.Controller;

import com.web.coloshop.Service.RoleService;
import com.web.coloshop.Service.UserService;
import com.web.coloshop.model.Role;
import com.web.coloshop.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;
import java.util.List;


@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @GetMapping("/SignUp")
    public String SignUp() {
        return "Admin/Sign_Up";
    }
    public static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());
            byte[] digest = md.digest();
            BigInteger bigInteger = new BigInteger(1, digest);
            String hashText = bigInteger.toString(16);
            while (hashText.length() < 32) {
                hashText = "0" + hashText;
            }
            return hashText;
        } catch (NoSuchAlgorithmException e) {
            // Xử lý nếu thuật toán MD5 không được hỗ trợ
            e.printStackTrace();
            return null;
        }
    }
    @PostMapping("/save_SignUp")
    public String saveSignUp(@ModelAttribute User user, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, RedirectAttributes attributes){

        user.setFullname(firstName + " " + lastName);
        Role role = roleService.findById(2L); // Giả sử roleService cung cấp phương thức findById

        User emailExists = userService.findByEmail(user.getEmail());
        if (emailExists != null) {
            attributes.addFlashAttribute("error", "Category with the specified ID not found.");
            return "redirect:/Admin/SignUp";
        }else {
            user.setRole(role);
            user.setPass(md5(user.getPass()));
            user.set_activated(true);
            user.set_deleted(false);
            userService.save(user);

        }
        return "redirect:/Admin/SignUp";
    }
    @GetMapping("/Login")
    public String Login() {
        return "Admin/Login";
    }
    @PostMapping("/Do_Login")
    public String DoLogin(@ModelAttribute User user, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, RedirectAttributes attributes){


        return "redirect:/Admin/SignUp";
    }
    @GetMapping("/Admin/Manager_User")
    public String listManager_User(Model model){
        List<User> users = userService.findAll();
        List<Role> roles= roleService.findAll();
        model.addAttribute("roles",roles);
        model.addAttribute("users", users);
        return "/Admin/ManagerUser";
    }
    @PostMapping("/Update_User_Role")
    public String updateUserRole(@PathVariable Long id,@ModelAttribute User user, RedirectAttributes attributes){


        return "redirect:/Admin/Manager_User";
    }
    @RequestMapping(value = "/delete-User", method = {RequestMethod.PUT, RequestMethod.GET})
    public String delete(Long id,Model model){
        try {
            userService.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/Admin/Manager_User";
    }

    @RequestMapping(value = "/enable-User", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enable(Long id,Model model){
        try {
            userService.enabledById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/Admin/Manager_User";
    }

}
