package pl.mzuchnik.commonlogger.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class LogBookAutoConfiguration {

    public LogBookAutoConfiguration() {
        log.info("LogBook AutoConfiguration initialized");
    }

}
