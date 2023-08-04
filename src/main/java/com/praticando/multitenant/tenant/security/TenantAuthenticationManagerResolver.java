package com.praticando.multitenant.tenant.security;

import com.praticando.multitenant.tenant.context.TenantContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TenantAuthenticationManagerResolver implements AuthenticationManagerResolver<HttpServletRequest> {
    private static final Map<String,AuthenticationManager> authenticationManagers = new ConcurrentHashMap<>();
    @Override
    public AuthenticationManager resolve(HttpServletRequest request) {
        var tenant = TenantContext.getTenantId();
        return authenticationManagers.computeIfAbsent(tenant, this::buildAuthenticationManager);
    }

    private AuthenticationManager buildAuthenticationManager(String tenantId) {
        var jwtAuthenticationprovider = new JwtAuthenticationProvider(JwtDecoders.fromIssuerLocation(tenantId));
        return jwtAuthenticationprovider::authenticate;
    }
}
