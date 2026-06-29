package com.ex.passagiram.service;


import com.ex.passagiram.model.TrainingRequest;
import com.ex.passagiram.repository.TrainingRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TrainingRequestService {

    private final TrainingRequestRepository requestRepository;

    public TrainingRequestService(TrainingRequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public Map<String, Object> createRequest(TrainingRequest request) {
        if (request.getUserId() == null) {
            return Map.of(
                    "success", false,
                    "message", "Пользователь не найден"
            );
        }

        if (request.getTransportType() == null || request.getTransportType().isBlank()) {
            return Map.of(
                    "success", false,
                    "message", "Выберите вид транспорта"
            );
        }

        if (request.getStartDate() == null) {
            return Map.of(
                    "success", false,
                    "message", "Выберите дату начала обучения"
            );
        }

        if (request.getPaymentType() == null || request.getPaymentType().isBlank()) {
            return Map.of(
                    "success", false,
                    "message", "Выберите способ оплаты"
            );
        }

        request.setStatus("Новая");

        TrainingRequest savedRequest = requestRepository.save(request);

        return Map.of(
                "success", true,
                "message", "Заявка отправлена на согласование",
                "requestId", savedRequest.getId()
        );
    }

    public List<TrainingRequest> getRequestsByUserId(Long userId) {
        return requestRepository.findByUserId(userId);
    }
}