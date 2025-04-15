package com.orass.repository;

import com.orass.model.DetailVictime;
import com.orass.model.DetailVictimeId;

import java.util.List;
// import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
// import org.springframework.stereotype.Repository;

public interface DetailVictimeRepository extends JpaRepository<DetailVictime, DetailVictimeId> {
    
    @Query("SELECT d FROM DetailVictime d WHERE d.email IS NOT NULL AND d.nbrJour >= :threshold")
    List<DetailVictime> findVictimesRequiringAlert(@Param("threshold") Integer threshold);

    @Query("SELECT DISTINCT d.creePar, COUNT(d) FROM DetailVictime d WHERE d.email IS NOT NULL AND d.nbrJour >= :threshold GROUP BY d.creePar")
    List<Object[]> countAlertsByGestionnaire(@Param("threshold") Integer threshold);
}
