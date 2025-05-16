package pl.mzuchnik.metricalertsms;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.mzuchnik.metricalertsms.model.AlertEntity;
import pl.mzuchnik.metricalertsms.repository.AlertEntityRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alerts")
class AlertsApi {

    private final AlertEntityRepository alertEntityRepository;

    public AlertsApi(AlertEntityRepository alertEntityRepository) {
        this.alertEntityRepository = alertEntityRepository;
    }

    @GetMapping
    public List<AlertEntity> getAlerts(
            @RequestParam(required = false, name = "application") String app,
            @RequestParam(required = false, name = "priority") String priority,
            @RequestParam(required = false, name = "type") String type) {

        if (app == null || priority == null) {
            return alertEntityRepository.findAll();
        } else if (!app.isEmpty() && !priority.isEmpty()) {
            return alertEntityRepository.findAlertEntitiesByApplicationIgnoreCaseAndPriorityIgnoreCase(app, priority);
        } else if (app.isEmpty() && !priority.isEmpty()) {
            return alertEntityRepository.findAlertEntitiesByPriorityIgnoreCase(priority);
        } else {
            return alertEntityRepository.findAlertEntitiesByApplicationIgnoreCase(app);
        }
    }
}
