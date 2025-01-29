package com.example.api_reservations.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/documentation")
public class DocumentationController {

    @Operation(summary = "Redirect to Swagger UI", description = "Redirects to the Swagger UI page for API documentation")
    @GetMapping
    public String redirectToSwaggerUi() {
        return "redirect:/swagger-ui.html";
    }

    @Hidden
    @GetMapping("/openapi")
    public String redirectToOpenApiDocs() {
        return "redirect:/api-docs";
    }
}
