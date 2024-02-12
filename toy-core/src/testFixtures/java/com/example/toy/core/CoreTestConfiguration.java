package com.example.toy.core;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackages = "com.example.toy")
@EntityScan(basePackages = {"com.example.toy.core"})
public class CoreTestConfiguration {

}
