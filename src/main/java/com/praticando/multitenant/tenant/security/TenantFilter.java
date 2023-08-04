package com.praticando.multitenant.tenant.security;

import com.praticando.multitenant.tenant.context.TenantContext;
import com.praticando.multitenant.tenant.resolver.HttpHeaderTenantResolver;
import io.micrometer.common.KeyValue;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.filter.ServerHttpObservationFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TenantFilter extends OncePerRequestFilter {

    private  final HttpHeaderTenantResolver httpHeaderTenantResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional.ofNullable(httpHeaderTenantResolver.resolveTenantId(request)).ifPresent(tenantId -> {
            TenantContext.setTenantId(tenantId);
            configureTraces(tenantId, request);
        });

        filterChain.doFilter(request, response);

        clear();
    }

    private void configureTraces(String tenantId, HttpServletRequest request) {
        ServerHttpObservationFilter.findObservationContext(request).ifPresent(context ->
                context.addHighCardinalityKeyValue(KeyValue.of("tenant.id", tenantId)));
    }

    private void clear() {
        TenantContext.clear();
    }
}
