
package io.quarkus.kubernetes.service.binding.buildtime;

import static io.quarkus.kubernetes.service.binding.buildtime.ServiceBindingProcessor.createRequirement;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import io.quarkus.kubernetes.service.binding.spi.ServiceQualifierBuildItem;
import io.quarkus.kubernetes.service.binding.spi.ServiceRequirementBuildItem;

class ServiceBindingProcessorTest {

    @Test
    public void testFullyAutomaticPostgresConfiguration() throws Exception {
        Optional<ServiceRequirementBuildItem> requirement = createRequirement("app", new KubernetesServiceBindingConfig(),
                new ServiceQualifierBuildItem("postgresql", "default"));
        assertTrue(requirement.isPresent());
        requirement.ifPresent(r -> {
            assertEquals("postgres-operator.crunchydata.com/v1beta1", r.getApiVersion());
            assertEquals("PostgresCluster", r.getKind());
            assertEquals("postgresql-default", r.getName());
            assertEquals("app-postgresql-default", r.getBinding());

        });
    }

    @Test
    public void testSemiAutomaticPostgresConfiguration() throws Exception {
        KubernetesServiceBindingConfig userConfig = new KubernetesServiceBindingConfig();
        userConfig.services = new HashMap<>();
        userConfig.services.put("postgresql-default",
                ServiceConfig.createNew().withName("my-postgresql").withBinding("custom-binding"));

        Optional<ServiceRequirementBuildItem> requirement = createRequirement("app", userConfig,
                new ServiceQualifierBuildItem("postgresql", "default"));
        assertTrue(requirement.isPresent());
        requirement.ifPresent(r -> {
            assertEquals("postgres-operator.crunchydata.com/v1beta1", r.getApiVersion());
            assertEquals("PostgresCluster", r.getKind());
            assertEquals("my-postgresql", r.getName());
            assertEquals("custom-binding", r.getBinding());

        });
    }

    @Test
    public void testManualPostgresConfiguration() throws Exception {
        KubernetesServiceBindingConfig userConfig = new KubernetesServiceBindingConfig();
        userConfig.services = new HashMap<>();
        userConfig.services.put("my-postgresql",
                ServiceConfig.createNew()
                        .withApiVersion("foo/v1")
                        .withKind("PostgresDB")
                        .withBinding("custom-binding"));

        Optional<ServiceRequirementBuildItem> requirement = createRequirement("app", "my-postgresql", userConfig);
        assertTrue(requirement.isPresent());
        requirement.ifPresent(r -> {
            assertEquals("foo/v1", r.getApiVersion());
            assertEquals("PostgresDB", r.getKind());
            assertEquals("my-postgresql", r.getName());
            assertEquals("custom-binding", r.getBinding());

        });
    }

    @Test
    public void testFullyAutomaticMysqlConfiguration() throws Exception {
        Optional<ServiceRequirementBuildItem> requirement = createRequirement("app", new KubernetesServiceBindingConfig(),
                new ServiceQualifierBuildItem("mysql", "default"));
        assertTrue(requirement.isPresent());
        requirement.ifPresent(r -> {
            assertEquals("pxc.percona.com/v1-9-0", r.getApiVersion());
            assertEquals("PerconaXtraDBCluster", r.getKind());
            assertEquals("mysql-default", r.getName());
            assertEquals("app-mysql-default", r.getBinding());

        });
    }

    @Test
    public void testSemiAutomaticMysqlConfiguration() throws Exception {
        KubernetesServiceBindingConfig userConfig = new KubernetesServiceBindingConfig();
        userConfig.services = new HashMap<>();
        userConfig.services.put("mysql-default",
                ServiceConfig.createNew().withName("my-mysql").withApiVersion("some.group/v1").withKind("Mysql"));

        ServiceBindingQualifierBuildItem qualifier = new ServiceBindingQualifierBuildItem("mysql", "default");
        Optional<ServiceBindingRequirementBuildItem> requirement = createRequirement("app", userConfig, qualifier);
        assertTrue(requirement.isPresent());
        requirement.ifPresent(r -> {
            assertEquals("some.group/v1", r.getApiVersion());
            assertEquals("Mysql", r.getKind());
            assertEquals("my-mysql", r.getName());
            assertEquals("app-mysql-default", r.getBinding());

        });
    }

    @Test
    public void testManualMysqlConfiguration() throws Exception {
        KubernetesServiceBindingConfig userConfig = new KubernetesServiceBindingConfig();
        userConfig.services = new HashMap<>();
        userConfig.services.put("my-mysql",
                ServiceConfig.createNew()
                        .withApiVersion("foo/v1")
                        .withKind("Bar")
                        .withName("custom-name")
                        .withBinding("custom-binding"));

        Optional<ServiceRequirementBuildItem> requirement = createRequirement("app", "my-mysql", userConfig);
        assertTrue(requirement.isPresent());
        requirement.ifPresent(r -> {
            assertEquals("foo/v1", r.getApiVersion());
            assertEquals("Bar", r.getKind());
            assertEquals("custom-name", r.getName());
            assertEquals("custom-binding", r.getBinding());

        });
    }

    @Test
    public void testFullyAutomaticMongoConfiguration() throws Exception {
        Optional<ServiceRequirementBuildItem> requirement = createRequirement("app", new KubernetesServiceBindingConfig(),
                new ServiceQualifierBuildItem("mongodb", "default"));
        assertTrue(requirement.isPresent());
        requirement.ifPresent(r -> {
            assertEquals("psmdb.percona.com/v1-9-0", r.getApiVersion());
            assertEquals("PerconaServerMongoDB", r.getKind());
            assertEquals("mongodb-default", r.getName());
            assertEquals("app-mongodb-default", r.getBinding());

        });
    }

    @Test
    public void testSemiAutomaticMongoConfiguration() throws Exception {
        KubernetesServiceBindingConfig userConfig = new KubernetesServiceBindingConfig();
        userConfig.services = new HashMap<>();
        userConfig.services.put("mongodb-default", ServiceConfig.createNew().withName("my-mongo"));

        Optional<ServiceRequirementBuildItem> requirement = createRequirement("app", userConfig,
                new ServiceQualifierBuildItem("mongodb", "default"));
        assertTrue(requirement.isPresent());
        requirement.ifPresent(r -> {
            assertEquals("psmdb.percona.com/v1-9-0", r.getApiVersion());
            assertEquals("PerconaServerMongoDB", r.getKind());
            assertEquals("my-mongo", r.getName());
            assertEquals("app-mongodb-default", r.getBinding());

        });
    }

    @Test
    public void testManualMongoConfiguration() throws Exception {
        KubernetesServiceBindingConfig userConfig = new KubernetesServiceBindingConfig();
        userConfig.services = new HashMap<>();
        userConfig.services.put("my-mongodb",
                ServiceConfig.createNew()
                        .withApiVersion("foo/v1")
                        .withKind("MongoDB")
                        .withName("custom-name")
                        .withBinding("custom-binding"));

        Optional<ServiceRequirementBuildItem> requirement = createRequirement("app", "my-mongodb", userConfig);
        assertTrue(requirement.isPresent());
        requirement.ifPresent(r -> {
            assertEquals("foo/v1", r.getApiVersion());
            assertEquals("MongoDB", r.getKind());
            assertEquals("custom-name", r.getName());
            assertEquals("custom-binding", r.getBinding());

        });
    }

}
