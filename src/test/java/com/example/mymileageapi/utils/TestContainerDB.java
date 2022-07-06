package com.example.mymileageapi.utils;

import org.testcontainers.containers.MariaDBContainer;

public class TestContainerDB extends MariaDBContainer<TestContainerDB> {
    private static final String version = "mariadb:10.3";
    private static TestContainerDB container;

    private TestContainerDB() {
        super(version);
    }

    public static TestContainerDB getInstance() {
        if (container == null) {
            container = new TestContainerDB()
                    .withUsername("jdragon")
                    .withPassword("1111")
                    .withDatabaseName("triple")
                    .withReuse(true)
                    .withCommand("--character-set-server=utf8mb4", "--collation-server=utf8mb4_unicode_ci");
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {

    }
}
