package brockresource.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import brockresource.domain.User;
import brockresource.repositories.UserRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String register(@AuthenticationPrincipal User principal, ModelMap model) {
        if (!principal.getUsername().equals("pranjal")) {
            throw new IllegalTransactionStateException("Unauthorized");
        }

        model.put("user", new User());
        return "register";
    }

    @PostMapping
    public String saveUser(@Valid @ModelAttribute User user, BindingResult result,
            @AuthenticationPrincipal User principal, ModelMap model) {

        if (!principal.getUsername().equals("pranjal")) {
            throw new IllegalTransactionStateException("Unauthorized");
        }

        if (result.hasErrors()) {
            model.put("user", user);
            return "register";
        }

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);

        return "redirect:/resource/search";
    }

    @GetMapping("/update")
    public String changePassView(@AuthenticationPrincipal User principal, ModelMap model) {
        model.put("user", principal);
        return "update";
    }

    @PostMapping("/update")
    public String changePass(@Valid @ModelAttribute User user, BindingResult result,
            ModelMap model, @AuthenticationPrincipal User principal) {

        if (result.hasErrors()) {
            model.put("user", user);
            return "update";
        }

        User dbUser = userRepository.findByUsername(principal.getUsername());
        dbUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(dbUser);

        return "redirect:/resource/search";
    }
}
