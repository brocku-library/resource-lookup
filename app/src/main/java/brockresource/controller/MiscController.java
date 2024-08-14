package brockresource.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MiscController {
    
    @GetMapping("/")
    public String redirectToSearch() {
        return "redirect:/resource/search";
    }

    /* 
     * Global error page. Contains a link to search page.
     */
    @GetMapping("/error")
    public String error() {
        return "error";
    }
}
