package ru.mcx73.gis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mcx73.gis.entity.User;
import ru.mcx73.gis.service.UserService;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    /*
    получает данные всех пользователей и добавляет их на страницу.
     */
    @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "admin";
    }

    @PostMapping("/admin")
    public String  deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
                              @RequestParam(required = true, defaultValue = "" ) String action,
                              Model model) {
        if (action.equals("delete")){
            userService.deleteUser(userId);
        }
        return "redirect:/admin";
    }

    /*
    PathVariable. С помощью этой аннотации мы получаем отдельные части URL, для метод getUser() URL будет выглядеть следующим
     образом: http://localhost:8080/admin/gt/24, после перехода выведется список всех пользователей с id>24.
     */

    @GetMapping("/admin/gt/{userId}")
    public String  gtUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("allUsers", userService.usergtList(userId));
        return "admin";
    }

    /*
    AuthenticationPrincipal - получает пользователя с контекста, чтобы не искать его в БД
     */
    @GetMapping("/profile")
    public String  getProfile(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("password", user.getPassword());
        model.addAttribute("passwordConfirm", user.getPasswordConfirm());
        return "profile";
    }

    @PostMapping("/profile")
    public String  updateProfile(@AuthenticationPrincipal User user,
                                 @RequestParam String username,
                                 @RequestParam String email,
                                 @RequestParam String password) {

            userService.updateProfile(user,username,email,password);

        return "redirect:/profile";
    }

}
