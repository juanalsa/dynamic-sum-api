package com.dynamic.dynamic_sum_api.repository;

import com.dynamic.dynamic_sum_api.model.entity.CallLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CallLogRepository extends JpaRepository<CallLog, UUID> {
}
