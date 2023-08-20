package com.ami.config;

import com.ams.config.SpringConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
@Import(SpringConfig.class)
public class SpringTestConfig {
}
