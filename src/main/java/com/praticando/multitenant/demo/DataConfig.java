package com.praticando.multitenant.demo;

import com.praticando.multitenant.entity.Car;
import com.praticando.multitenant.repository.CarRepository;
import com.praticando.multitenant.tenant.context.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.List;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class DataConfig {

    private final CarRepository carRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void loadTestData() {
        TenantContext.setTenantId("honda");
        if (carRepository.count() == 0) {
            var city = new Car();
            city.setName("city");
            city.setColor("vermelho");
            city.setBrand("volkswagen");

            var civic = new Car();
            civic.setName("civic");
            civic.setColor("vermelho");
            civic.setBrand("toyota");

            var hrv = new Car();
            hrv.setName("hrv");
            hrv.setColor("vermelho");
            hrv.setBrand("toyota");
            carRepository.saveAll(List.of(city, civic, hrv));
        }
        TenantContext.clear();

        TenantContext.setTenantId("toyota");
        if (carRepository.count() == 0) {
            var sw4 = new Car();
            sw4.setName("sw4");
            sw4.setColor("vermelho");
            sw4.setBrand("volkswagen");
            var yaris = new Car();
            yaris.setName("yaris");
            yaris.setColor("vermelho");
            yaris.setBrand("toyota");

            var corolla = new Car();
            corolla.setName("corolla");
            corolla.setColor("vermelho");
            corolla.setBrand("toyota");
            carRepository.saveAll(List.of(corolla, yaris, sw4));
        }
        TenantContext.clear();
    }
}
