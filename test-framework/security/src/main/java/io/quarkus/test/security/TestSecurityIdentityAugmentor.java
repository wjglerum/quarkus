package io.quarkus.test.security;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.Supplier;

import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.SecurityIdentityAugmentor;

public interface TestSecurityIdentityAugmentor {
    SecurityIdentity augment(SecurityIdentity identity, Annotation[] annotations);

    /**
     * Augmentors that the test authentication mechanism applies to the test security identity
     * on every request, without requiring the {@link TestSecurity#augmentors()} opt-in. Unlike
     * {@link #augment(SecurityIdentity, Annotation[])}, which runs once per test before any
     * request is made, these augmentors can enforce per-request security constraints that are
     * otherwise only enforced during a real authentication, such as the OIDC
     * {@code @AuthenticationContext} step-up authentication policy. They are returned here
     * rather than defined as CDI beans to avoid augmenting identities produced outside of the
     * test authentication mechanism.
     */
    default List<Supplier<? extends SecurityIdentityAugmentor>> perRequestAugmentors() {
        return List.of();
    }
}
