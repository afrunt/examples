package com.afrunt.examples.springboot.admin;

import com.hazelcast.config.*;
import com.hazelcast.map.merge.PutIfAbsentMapMergePolicy;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author Andrii Frunt
 */
@EnableAdminServer
@SpringBootApplication
public class AdminServer {
    @Bean
    public Config hazelcastConfig() {
        return new Config()
                .setProperty("hazelcast.jmx", "true")
                .addMapConfig(new MapConfig("spring-boot-admin-event-store")
                        .setInMemoryFormat(InMemoryFormat.OBJECT)
                        .setBackupCount(1)
                        .setEvictionPolicy(EvictionPolicy.NONE)
                        .setMergePolicyConfig(new MergePolicyConfig(
                                PutIfAbsentMapMergePolicy.class.getName(),
                                100
                        )));
    }

    public static void main(String[] args) {
        SpringApplication.run(AdminServer.class, args);
    }
}
