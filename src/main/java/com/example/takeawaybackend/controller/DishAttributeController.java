package com.example.takeawaybackend.controller;

import com.example.takeawaybackend.dao.DishAttributeDao;
import com.example.takeawaybackend.dao.DishFlavorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pre/dishAttribute")
public class DishAttributeController {
    @Autowired
    private DishAttributeDao dishAttributeDao;
    @Autowired
    private DishFlavorDao dishFlavorDao;

}
