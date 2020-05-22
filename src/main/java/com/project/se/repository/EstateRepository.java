package com.project.se.repository;

import com.project.se.domain.Estate;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface EstateRepository extends PagingAndSortingRepository<Estate, Integer> {
}
