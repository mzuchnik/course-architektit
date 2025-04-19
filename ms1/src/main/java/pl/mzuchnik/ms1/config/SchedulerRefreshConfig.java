package pl.mzuchnik.ms1.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Set;

@Configuration
@EnableScheduling
public class SchedulerRefreshConfig {

    private static final Logger log = LoggerFactory.getLogger(SchedulerRefreshConfig.class);
    private final ContextRefresher refresher;

    public SchedulerRefreshConfig(@Qualifier(value = "configDataContextRefresher") ContextRefresher refresher) {
        this.refresher = refresher;
    }

    @Scheduled(cron = "0 * * * * *")
    public void refresh() {
        log.info("Refreshing data");
        Set<String> refresh = refresher.refresh();
        log.info("Refreshing data completed: {}", refresh);
    }
}
