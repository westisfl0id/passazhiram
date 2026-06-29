package com.ex.passagiram.service;

import com.ex.passagiram.model.TrainingRequest;
import com.ex.passagiram.repository.TrainingRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    private final TrainingRequestRepository requestRepository;

    public AdminService(TrainingRequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }
    
    public List<TrainingRequest> getAllRequests() {
        return requestRepository.findAll();
    }

    public Map<String, Object> updateRequestStatus(Long requestId, String status) {
        if (!isValidStatus(status)) {
            return Map.of(
                    "success", false,
                    "message", "Недопустимый статус заявки"
            );
        }

        TrainingRequest request = requestRepository.findById(requestId)
                .orElse(null);

        if (request == null) {
            return Map.of(
                    "success", false,
                    "message", "Заявка не найдена"
            );
        }

        request.setStatus(status);
        requestRepository.save(request);

        return Map.of(
                "success", true,
                "message", "Статус заявки обновлен"
        );
    }

    private boolean isValidStatus(String status) {
        return "Новая".equals(status)
                || "Идет обучение".equals(status)
                || "Обучение завершено".equals(status);
    }
}