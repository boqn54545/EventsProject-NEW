package com.example.EventsProject.Repositories;

import com.example.EventsProject.Entities.Ad;
import com.example.EventsProject.Entities.User;
import com.example.EventsProject.Enums.InterestsEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AdsRepository extends JpaRepository<Ad, Long> {
    @Query("SELECT a FROM Ad a WHERE " +
            "(:title IS NULL OR a.title LIKE %:title%) " +
            "AND (:interest IS NULL OR a.interest = :interest) " +
            "AND (:priceMin IS NULL OR a.minPrice >= :priceMin) " +
            "AND (:priceMax IS NULL OR a.maxPrice <= :priceMax) " +
            "AND (:city IS NULL OR a.city = :city) " +
            "AND (:minAge IS NULL OR a.minAge BETWEEN :minAge AND :maxAge) " +
            "AND (:maxAge IS NULL OR a.maxAge BETWEEN :minAge AND :maxAge)")

    List<Ad> customSearch(@Param("title") String title,
                          @Param("interest") String interest,
                          @Param("priceMin") Optional<Integer> priceMin,
                          @Param("priceMax") Optional<Integer> priceMax,
                          @Param("city") String city,
                          @Param("minAge") Optional<Integer> minAge,
                          @Param("maxAge") Optional<Integer> maxAge);
}