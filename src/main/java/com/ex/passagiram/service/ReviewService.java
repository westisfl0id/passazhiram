package com.ex.passagiram.service;

import com.ex.passagiram.model.Review;
import com.ex.passagiram.model.TrainingRequest;
import com.ex.passagiram.repository.ReviewRepository;
import com.ex.passagiram.repository.TrainingRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final TrainingRequestRepository requestRepository;

    public ReviewService(
            ReviewRepository reviewRepository,
            TrainingRequestRepository requestRepository
    ) {
        this.reviewRepository = reviewRepository;
        this.requestRepository = requestRepository;
    }

    public Map<String, Object> createReview(Review review) {
        if (review.getUserId() == null) {
            return Map.of(
                    "success", false,
                    "message", "Пользователь не найден"
            );
        }

        if (review.getRequestId() == null) {
            return Map.of(
                    "success", false,
                    "message", "Заявка не найдена"
            );
        }

        if (review.getText() == null || review.getText().isBlank()) {
            return Map.of(
                    "success", false,
                    "message", "Введите текст отзыва"
            );
        }

        TrainingRequest request = requestRepository.findById(review.getRequestId())
                .orElse(null);

        if (request == null) {
            return Map.of(
                    "success", false,
                    "message", "Заявка не найдена"
            );
        }

        if (!"Обучение завершено".equals(request.getStatus())) {
            return Map.of(
                    "success", false,
                    "message", "Оставить отзыв можно только после завершения обучения"
            );
        }

        if (reviewRepository.existsByRequestId(review.getRequestId())) {
            return Map.of(
                    "success", false,
                    "message", "Отзыв на эту заявку уже оставлен"
            );
        }

        reviewRepository.save(review);

        return Map.of(
                "success", true,
                "message", "Отзыв сохранен"
        );
    }

    public List<Review> getReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId);
    }
}