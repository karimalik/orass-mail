package com.orass.orassmail.repository;

import com.orass.orassmail.model.AlerteEnvoyee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
// import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

public interface AlerteEnvoyeeRepository extends JpaRepository<AlerteEnvoyee, Long> {

        @Query("SELECT a FROM AlerteEnvoyee a WHERE a.codeInte = :codeInte AND a.exerSini = :exerSini AND a.numeSini = :numeSini "
                        +
                        "AND a.numeTier = :numeTier AND a.typeAlerte = :typeAlerte AND a.dateEnvoi > :dateLimite")
        List<AlerteEnvoyee> findRecentAlertesForVictime(
                        @Param("codeInte") String codeInte,
                        @Param("exerSini") String exerSini,
                        @Param("numeSini") String numeSini,
                        @Param("numeTier") String numeTier,
                        @Param("typeAlerte") String typeAlerte,
                        @Param("dateLimite") Date dateLimite);

}
