package org.example.webrise.repository;

import org.example.webrise.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {

    Optional<Subscription> findByServiceName(String serviceName);

    @Query("SELECT s FROM Subscription s GROUP BY s.serviceName ORDER BY COUNT(s) DESC")
    List<Subscription> findTopSubscriptions();
}
