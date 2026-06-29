package com.ex.passagiram.controller;


import com.ex.passagiram.model.Review;
import com.ex.passagiram.model.TrainingRequest;
import com.ex.passagiram.repository.ReviewRepository;
import com.ex.passagiram.repository.TrainingRequestRepository;
import com.ex.passagiram.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public Map<String, Object> createReview(@RequestBody Review review) {
        return reviewService.createReview(review);
    }

    @GetMapping("/user/{userId}")
    public List<Review> getUserReviews(@PathVariable Long userId) {
        return reviewService.getReviewsByUserId(userId);
    }
//    private final ReviewRepository reviewRepository;
//    private final TrainingRequestRepository requestRepository;
//
//    public ReviewController(
//            ReviewRepository reviewRepository,
//            TrainingRequestRepository requestRepository
//    ) {
//        this.reviewRepository = reviewRepository;
//        this.requestRepository = requestRepository;
//    }
//
//    @PostMapping
//    public Map<String, Object> createReview(@RequestBody Review review) {
//        TrainingRequest request = requestRepository.findById(review.getRequestId())
//                .orElseThrow(() -> new RuntimeException("Заявка не найдена"));
//
//        if (!"Обучение завершено".equals(request.getStatus())) {
//            return Map.of(
//                    "success", false,
//                    "message", "Оставить отзыв можно только после завершения обучения"
//            );
//        }
//
//        reviewRepository.save(review);
//
//        return Map.of(
//                "success", true,
//                "message", "Отзыв сохранен"
//        );
//    }
}
