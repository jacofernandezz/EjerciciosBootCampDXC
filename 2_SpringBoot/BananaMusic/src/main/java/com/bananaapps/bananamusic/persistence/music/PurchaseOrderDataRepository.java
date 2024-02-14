package com.bananaapps.bananamusic.persistence.music;

import com.bananaapps.bananamusic.domain.music.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderDataRepository extends PurchaseOrderRepository, JpaRepository<PurchaseOrder, Long> {
}
