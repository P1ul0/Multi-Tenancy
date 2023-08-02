package com.praticando.multitenant.controller;

import com.praticando.multitenant.entity.Car;
import com.praticando.multitenant.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarRepository carRepository;

    @GetMapping
    public ResponseEntity<List<Car>> getCars() {
        return ResponseEntity.ok(carRepository.findAll());
    }

}
