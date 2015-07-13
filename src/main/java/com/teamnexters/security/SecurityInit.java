package com.teamnexters.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;


public class SecurityInit extends AbstractSecurityWebApplicationInitializer {

    public SecurityInit() {
        super(SecurityConfig.class);
    }
}