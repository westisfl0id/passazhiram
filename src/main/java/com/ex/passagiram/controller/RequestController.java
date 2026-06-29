package com.ex.passagiram.controller;


import com.ex.passagiram.model.TrainingRequest;
import com.ex.passagiram.repository.TrainingRequestRepository;
import com.ex.passagiram.service.TrainingRequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    private final TrainingRequestService requestService;

    public RequestController(TrainingRequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping
    public Map<String, Object> createRequest(@RequestBody TrainingRequest request) {
        return requestService.createRequest(request);
    }

    @GetMapping("/user/{userId}")
    public List<TrainingRequest> getUserRequests(@PathVariable Long userId) {
        return requestService.getRequestsByUserId(userId);
    }
//    private final TrainingRequestRepository requestRepository;
//
//    public RequestController(TrainingRequestRepository requestRepository) {
//        this.requestRepository = requestRepository;
//    }
//
//    @PostMapping
//    public Map<String, Object> create(@RequestBody TrainingRequest request) {
//        request.setStatus("Новая");
//        TrainingRequest saved = requestRepository.save(request);
//
//        return Map.of(
//                "success", true,
//                "message", "Заявка отправлена на согласование",
//                "requestId", saved.getId()
//        );
//    }
//
//    @GetMapping("/user/{userId}")
//    public List<TrainingRequest> getUserRequests(@PathVariable Long userId) {
//        return requestRepository.findByUserId(userId);
//    }
}
