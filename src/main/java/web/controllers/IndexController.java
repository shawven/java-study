package web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author FS
 * @date 2018-10-08 10:29
 */
@Controller
public class IndexController {

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @GetMapping({"/", "index"})
    public String index() {
        return "index";
    }

    @GetMapping("home")
    public String home() {
        return "home";
    }

    @GetMapping("passport")
    public String passport() {
        return "passport";
    }

    @GetMapping("special")
    public String special() {
        return "special";
    }
}
