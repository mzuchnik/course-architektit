package pl.mzuchnik.metricalertsms.repository;

import org.springframework.data.repository.ListCrudRepository;
import pl.mzuchnik.metricalertsms.model.AlertEntity;

import java.util.List;

public interface AlertEntityRepository extends ListCrudRepository<AlertEntity, String> {

    List<AlertEntity> findAlertEntitiesByApplicationIgnoreCaseAndPriorityIgnoreCase(String application, String priority);

    List<AlertEntity> findAlertEntitiesByPriorityIgnoreCase(String priority);

    List<AlertEntity> findAlertEntitiesByApplicationIgnoreCase(String application);
}
