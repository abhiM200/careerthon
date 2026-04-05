package com.careerthon.repository;

import com.careerthon.model.ResumeReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeReviewRepository extends JpaRepository<ResumeReview, Long> {
    List<ResumeReview> findByUserEmail(String userEmail);
    List<ResumeReview> findAllByOrderByUploadedAtDesc();
}
