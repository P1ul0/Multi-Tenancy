package com.praticando.multitenant.tenant.web;

import com.praticando.multitenant.tenant.context.TenantContext;
import com.praticando.multitenant.tenant.resolver.HttpHeaderTenantResolver;
import io.micrometer.common.KeyValue;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.reactive.ServerHttpObservationFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@RequiredArgsConstructor
public class TenantInterceptor implements HandlerInterceptor {

    private final HttpHeaderTenantResolver httpHeaderTenantResolver;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String tenantId = httpHeaderTenantResolver.resolveTenantId(request);
        TenantContext.setTenantId(tenantId);
        configureTraces(tenantId, request);
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        clear();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        clear();
    }

    private void configureTraces(String tenantId, HttpServletRequest request) {
        ServerHttpObservationFilter.findObservationContext((ServerWebExchange) request).ifPresent(context ->
                context.addHighCardinalityKeyValue(KeyValue.of("tenant.id", tenantId)));
    }

    private void clear() {
        TenantContext.clear();
    }
}
