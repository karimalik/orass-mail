package com.orass.orassmail.repository;

import com.orass.orassmail.model.DetailVictime;
import com.orass.orassmail.model.DetailVictimeId;

import java.util.List;
// import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
// import org.springframework.stereotype.Repository;

public interface DetailVictimeRepository extends JpaRepository<DetailVictime, DetailVictimeId> {

    @Query("SELECT d FROM DetailVictime d WHERE d.reliquat > 0")
    List<DetailVictime> findSinistresNonRegles();
    
    @Query("SELECT d FROM DetailVictime d WHERE d.nbrJour >= :threshold")
    List<DetailVictime> findVictimesRequiringAlert(@Param("threshold") Integer
    threshold);

    @Query("SELECT DISTINCT d.creePar, COUNT(d) FROM DetailVictime d WHERE d.nbrJour >= :threshold GROUP BY d.creePar")
    List<Object[]> countAlertsByGestionnaire(@Param("threshold") Integer
    threshold);
}
