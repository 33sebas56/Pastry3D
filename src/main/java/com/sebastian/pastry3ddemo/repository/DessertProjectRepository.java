package com.sebastian.pastry3ddemo.repository;

import com.sebastian.pastry3ddemo.entity.DessertProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DessertProjectRepository extends JpaRepository<DessertProject, UUID> {

    List<DessertProject> findByUserIdOrderByCreatedAtDesc(UUID userId);
}
