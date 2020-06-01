package barter.barter_it_api.api.swagger

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/")
class SwaggerAtHome {

    @GetMapping("/")
    fun swaggerUi(): String {
        return "redirect:/swagger-ui.html"
    }

}