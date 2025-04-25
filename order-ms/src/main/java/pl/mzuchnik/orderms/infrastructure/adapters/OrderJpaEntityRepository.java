package pl.mzuchnik.orderms.infrastructure.adapters;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface OrderJpaEntityRepository extends JpaRepository<OrderJpaEntity, UUID> {

}
