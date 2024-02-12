package com.example.toy.task;

import com.example.toy.core.CoreTestConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(CoreTestConfiguration.class)
public class TaskTestConfiguration {
}
