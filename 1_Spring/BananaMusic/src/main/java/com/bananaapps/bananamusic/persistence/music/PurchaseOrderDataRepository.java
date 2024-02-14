package com.bananaapps.bananamusic.persistence.music;

import com.bananaapps.bananamusic.domain.music.PurchaseOrder;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

@Profile("prod")
public interface PurchaseOrderDataRepository extends PurchaseOrderRepository, JpaRepository<PurchaseOrder, Long> {
}
