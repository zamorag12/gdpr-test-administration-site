package com.gdpr.unir.repository;

import com.gdpr.unir.model.Cities;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitiesRepository extends PagingAndSortingRepository<Cities, Long> {
    Page<Cities> findByNameContainingIgnoreCaseAndUserId(String name, Long userId, Pageable pageable);

    Page<Cities> findAllByUserId(Long userId, Pageable pageable);

    List<Cities> findAllByUserId(Long userId);
}
