package com.project.se.repository;

import com.project.se.domain.Estate;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstateRepository extends PagingAndSortingRepository<Estate, Integer> {
}
