package com.careerthon.repository;

import com.careerthon.model.ProfileReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProfileReviewRepository extends JpaRepository<ProfileReview, Long> {
    List<ProfileReview> findByStatusOrderByCreatedAtDesc(ProfileReview.ReviewStatus status);
    List<ProfileReview> findAllByOrderByCreatedAtDesc();
}
