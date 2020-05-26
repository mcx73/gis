package ru.mcx73.gis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.mcx73.gis.entity.Docs;
import ru.mcx73.gis.entity.Role;
import ru.mcx73.gis.entity.User;
import ru.mcx73.gis.repository.DocsRepository;
import ru.mcx73.gis.repository.RoleRepository;
import ru.mcx73.gis.service.RoleServiceImpl;
import ru.mcx73.gis.service.UserService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    RoleServiceImpl roleService;

    @Autowired
    DocsRepository docsRepository;

    @Value("${upload.path}")
    private String uploadPath;

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

        List<Role> addressList = roleService.AllRole();
        List<String> roleList = new ArrayList<>();
        for (Role roles : addressList) {
            roleList.add(roles.getName());
        }
        model.addAttribute("roleList", roleList);

        return "profile";
    }

    @PostMapping("/profile")
    public String  updateProfile(@AuthenticationPrincipal User user,
                                 @RequestParam String username,
                                 @RequestParam String email,
                                 @RequestParam String password)
                                  {
            userService.updateProfile(user,username,email,password);

        return "redirect:/profile";
    }

    @GetMapping("/docs")
    public String main(Model model) {
        Iterable<Docs> docs = docsRepository.findAll();

        model.addAttribute("docum", docs);

        return "docs";
    }

    @PostMapping("/docs")
    public String addDocs(@AuthenticationPrincipal User user,
                                 @RequestParam(value = "file") MultipartFile file,
                                 Model model) throws IOException {

        Docs doc = new Docs(user);

        String newUploadPath = "";

        newUploadPath = uploadPath+"/"+user.getUsername().toString();

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(newUploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(newUploadPath + "/" + resultFilename));

            doc.setFilename(resultFilename);
        }
        docsRepository.save(doc);

        Iterable<Docs> docum = docsRepository.findAll();

        model.addAttribute("docum", docum);
        


        return "docs";
    }
}
