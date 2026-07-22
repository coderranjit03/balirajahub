package com.balirajahub.repository;

import com.balirajahub.entity.Scheme;
import com.balirajahub.entity.enums.SchemeCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SchemeRepository extends JpaRepository<Scheme, Long> {

    @Query("""
            SELECT s
            FROM Scheme s
            WHERE
            (:category IS NULL OR s.category = :category)
            AND
            (:keyword IS NULL
                OR LOWER(s.title)
                LIKE LOWER(CONCAT('%', :keyword, '%')))
            """)
    Page<Scheme> searchSchemes(

            @Param("category")
            SchemeCategory category,

            @Param("keyword")
            String keyword,

            Pageable pageable
    );




}