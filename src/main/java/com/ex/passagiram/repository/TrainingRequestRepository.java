package com.ex.passagiram.repository;

import com.ex.passagiram.model.TrainingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingRequestRepository extends JpaRepository<TrainingRequest, Long> {
    List<TrainingRequest> findByUserId(Long userId);
}
