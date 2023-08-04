package com.praticando.multitenant.tenant.resolver;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class HttpHeaderTenantResolver implements TenantResolver<HttpServletRequest> {

    @Override
    public String resolveTenantId(@NonNull  HttpServletRequest request) {
        System.out.println(request.getHeader("X-TenantId"));
        return request.getHeader("X-TenantId");
    }
}
