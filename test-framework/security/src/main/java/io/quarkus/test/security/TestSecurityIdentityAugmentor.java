package io.quarkus.test.security;

import java.lang.annotation.Annotation;

import io.quarkus.security.identity.SecurityIdentity;
import io.vertx.ext.web.RoutingContext;

public interface TestSecurityIdentityAugmentor {
    SecurityIdentity augment(SecurityIdentity identity, Annotation[] annotations);

    /**
     * Augments the test security identity on every request. Unlike
     * {@link #augment(SecurityIdentity, Annotation[])}, which runs once per test before any
     * request is made, this method can enforce per-request security constraints that are
     * otherwise only enforced during a real authentication, such as the OIDC
     * {@code @AuthenticationContext} step-up authentication policy.
     */
    default SecurityIdentity augmentPerRequest(SecurityIdentity identity, RoutingContext routingContext) {
        return identity;
    }
}
