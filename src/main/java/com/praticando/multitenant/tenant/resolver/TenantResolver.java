package com.praticando.multitenant.tenant.resolver;

import io.micrometer.common.lang.NonNull;

@FunctionalInterface
public interface TenantResolver <T>{
    String resolveTenantId(@NonNull T object);
}
