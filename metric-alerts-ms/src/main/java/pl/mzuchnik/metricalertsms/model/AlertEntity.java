package pl.mzuchnik.metricalertsms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import pl.mzuchnik.commonalertmodel.Alert;

import java.time.Instant;

@RedisHash
public record AlertEntity(
        @Id String id,
        String type,
        String application,
        String instance,
        String priority,
        String value,
        long time,
        @TimeToLive Long ttl
) {
    public static AlertEntity of(String id, Alert alert, Long ttl) {
        return new AlertEntity(id, alert.type(), alert.application(), alert.instance(), alert.priority(), alert.value(), alert.timestamp(), ttl);
    }
}
