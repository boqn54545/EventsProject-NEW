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
            "AND (:city IS NULL OR a.city = :city) "
    )

    List<Ad> customSearch(@Param("title") String title,
                          @Param("interest") String interest,
                          @Param("city") String city);

}