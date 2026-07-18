package com.vbank.logging.repository;

import com.vbank.logging.entity.LogMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<LogMessage, Long> {
}
