package com.ex.passagiram.controller;

import com.ex.passagiram.model.TrainingRequest;
import com.ex.passagiram.repository.TrainingRequestRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final TrainingRequestRepository requestRepository;

    public AdminController(TrainingRequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @GetMapping("/requests")
    public List<TrainingRequest> getAllRequests() {
        return requestRepository.findAll();
    }

    @PutMapping("/requests/{id}/status")
    public Map<String, Object> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body
    ) {
        TrainingRequest request = requestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Заявка не найдена"));

        request.setStatus(body.get("status"));
        requestRepository.save(request);

        return Map.of(
                "success", true,
                "message", "Статус обновлен"
        );
    }
}
