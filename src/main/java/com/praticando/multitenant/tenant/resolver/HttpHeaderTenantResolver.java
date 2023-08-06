package com.praticando.multitenant.tenant.resolver;

import com.praticando.multitenant.repository.CarRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HttpHeaderTenantResolver implements TenantResolver<HttpServletRequest> {

    private final CarRepository carRepository;

    @Override
    public String resolveTenantId(@NonNull  HttpServletRequest request) {
        System.out.println(request.getHeader("X-TenantId"));
        return request.getHeader("X-TenantId");
    }
}
