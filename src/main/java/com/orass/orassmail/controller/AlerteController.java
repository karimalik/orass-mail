package com.orass.orassmail.controller;

import com.orass.orassmail.model.DetailVictime;
import com.orass.orassmail.service.AlerteService;
import com.orass.orassmail.service.DetailVictimeService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import java.util.List;

@RestController
@RequestMapping("/api/alertes")
@Slf4j
public class AlerteController {
    
    @Autowired
    private AlerteService alerteService;

    @Autowired
    private DetailVictimeService detailVictimeService;

/**
 * Handles the manual triggering of alerts via the REST API.
 * 
 * This endpoint logs the triggering action, invokes the manual check and 
 * sending of alerts through the AlerteService, and returns a JSON response 
 * with the result.
 * 
 * @return ResponseEntity containing a success flag, the number of alerts 
 *         sent, and the current timestamp.
 */

    @GetMapping("/trigger")
    public ResponseEntity<Map<String, Object>> triggerAlerts() {
        log.info("Déclenchement manuel des alertes via API REST");
        
        int alertesEnvoyees = alerteService.manualCheckAndSendAlerts();
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("alertesEnvoyees", alertesEnvoyees);
        response.put("timestamp", System.currentTimeMillis());
        
        return ResponseEntity.ok(response);
    }

    /**
     * Renvoie un statut UP si l'application fonctionne correctement.
     * 
     * @return un objet JSON avec un champ "status" valant "UP" et un champ "timestamp" valant la date courante
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", System.currentTimeMillis());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/non-regles")
    public ResponseEntity<List<DetailVictime>> getSinistresNonRegles() {
        List<DetailVictime> sinistres = detailVictimeService.getSinistresNonRegles();
        return ResponseEntity.ok(sinistres);
    }
}
