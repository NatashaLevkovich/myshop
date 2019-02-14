package web.command;

import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import services.UserService;
import web.command.utils.Encoder;

import javax.validation.Valid;



@Controller
public class RegistrationController {

    @Autowired
    UserService userService;

    @RequestMapping("/registration")
    public String getPage(ModelMap model){
        model.put("user", new User());
        return "reg";
    }

    @RequestMapping("registration/add")
    public String getRegPage(ModelMap model, @Valid User user, BindingResult bindingResult, @RequestParam(value = "pass2") String pass2) {

        if (!bindingResult.hasErrors()) {
            if (user != null) {
                if (userService.getUserByEmail(user.getEmail()) != null){
                    model.put("errorName", "Пользователь с таким e-mail уже существует");
                    model.put("user", user);
                    return "reg";
                }
                if (user.getPassword().equals(pass2)) {
                    user.setPassword(Encoder.hash(user.getPassword()));
                    user.setStatus("USER");
                    user = userService.save(user);
                    model.put("user", user);
                    model.put("registration", "Регистрация завершена успешно");
                    return "login";
                } else {
                    model.put("error", "Пароли не совпадают");
                    model.put("user", user);
                    return "reg";
                }
            }
        }
        model.put("errors", "Введены некорректные данные");
        model.put("user", user);
        return "reg";
    }
}
