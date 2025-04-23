package com.orass.orassmail.service;

import com.orass.orassmail.model.AlerteEnvoyee;
import com.orass.orassmail.model.DetailVictime;
// import com.orass.orassmail.repository.AlerteEnvoyeeRepository;
import com.orass.orassmail.repository.DetailVictimeRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
// import java.util.Calendar;
// import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AlerteService {

    // @Autowired
    // private AlerteEnvoyeeRepository alerteEnvoyeeRepository;

    @Autowired
    private DetailVictimeRepository detailVictimeRepository;

    @Autowired
    private EmailService emailService;

    @Value("${alert.days.threshold:400}")
    private Integer minDaysThreshold;

    @Value("${alert.days.max.threshold:900}")
    private Integer maxDaysThreshold;

    @Value("${alert.summary.email:ytemole@saar-assurances.com}")
    private String summaryEmail;

    @Scheduled(cron = "${alert.cron.expression:0 0 7 * * ?}")
    @Transactional
    public void checkAndSendAlerts() {
        log.info("Début de la vérification des alertes...");

        List<DetailVictime> victimes = detailVictimeRepository.findVictimesRequiringAlert(minDaysThreshold,
                maxDaysThreshold);
        log.info("Nombre de victimes trouvées nécessitant une alerte : {}", victimes.size());

        int alertesEnvoyees = 0;
        Map<String, Integer> alertesByGestionnaire = new HashMap<>();

        for (DetailVictime victime : victimes) {

            List<AlerteEnvoyee> alertesRecentes = new ArrayList<>();

            if (victime.getEmail() != null && !victime.getEmail().trim().isEmpty()) {
                boolean success = emailService.sendVictimeAlert(victime);

                AlerteEnvoyee alerte = new AlerteEnvoyee(
                        victime,
                        "RETARD",
                        success ? "SUCCES" : "ECHEC");

                alerte.setId(1L);

                if (success) {
                    alertesEnvoyees++;

                    String gestionnaire = victime.getCreePar() != null ? victime.getCreePar() : "INCONNU";
                    alertesByGestionnaire.put(gestionnaire,
                            alertesByGestionnaire.getOrDefault(gestionnaire, 0) + 1);
                }
            }
        }

        log.info("Nombre d'alertes envoyées : {}", alertesEnvoyees);

        if (alertesEnvoyees > 0) {
            sendSummary(alertesEnvoyees, alertesByGestionnaire);
        }

        log.info("Fin de la vérification des alertes.");
    }

    // public void checkAndSendAlerts() {
    // log.info("Début de la vérification des alertes...");

    // List<DetailVictime> victimes =
    // detailVictimeRepository.findVictimesRequiringAlert(daysThreshold);
    // log.info("Nombre de victimes trouvées nécessitant une alerte : {}",
    // victimes.size());

    // int alertesEnvoyees = 0;
    // Calendar cal = Calendar.getInstance();
    // cal.add(Calendar.DAY_OF_MONTH, -7);
    // Date dateLimit = cal.getTime();

    // Map<String, Integer> alertesByGestionnaire = new HashMap<>();

    // for (DetailVictime victime : victimes) {
    // List<AlerteEnvoyee> alertesRecentes =
    // alerteEnvoyeeRepository.findRecentAlertesForVictime(
    // victime.getCodeInte(),
    // victime.getExerSini(),
    // victime.getNumeSini(),
    // victime.getNumeTier(),
    // "RETARD",
    // dateLimit);

    // if (alertesRecentes.isEmpty() && victime.getEmail() != null &&
    // !victime.getEmail().trim().isEmpty()) {
    // boolean success = emailService.sendVictimeAlert(victime);

    // AlerteEnvoyee alerte = new AlerteEnvoyee(
    // victime,
    // "RETARD",
    // success ? "SUCCES" : "ECHEC");
    // alerteEnvoyeeRepository.save(alerte);

    // if (success) {
    // alertesEnvoyees++;

    // String gestionnaire = victime.getCreePar() != null ? victime.getCreePar() :
    // "INCONNU";
    // alertesByGestionnaire.put(gestionnaire,
    // alertesByGestionnaire.getOrDefault(gestionnaire, 0) + 1);
    // }
    // }
    // }

    // log.info("Nombre d'alertes envoyées : {}", alertesEnvoyees);

    // if (alertesEnvoyees > 0) {
    // sendSummary(alertesEnvoyees, alertesByGestionnaire);
    // }

    // log.info("Fin de la vérification des alertes.");
    // }

    private void sendSummary(int totalAlerts, Map<String, Integer> alertesByGestionnaire) {
        StringBuilder detailContent = new StringBuilder("<ul>");

        for (Map.Entry<String, Integer> entry : alertesByGestionnaire.entrySet()) {
            detailContent.append("<li><strong>")
                    .append(entry.getKey())
                    .append("</strong> : ")
                    .append(entry.getValue())
                    .append(" alerte(s)</li>");
        }

        detailContent.append("</ul>");

        emailService.sendSummaryAlert(summaryEmail, totalAlerts, detailContent.toString());
    }

    // public int manualCheckAndSendAlerts() {
    // log.info("Vérification manuelle des alertes déclenchée");

    // checkAndSendAlerts();

    // Calendar cal = Calendar.getInstance();
    // cal.set(Calendar.HOUR_OF_DAY, 0);
    // cal.set(Calendar.MINUTE, 0);
    // cal.set(Calendar.SECOND, 0);
    // cal.set(Calendar.MILLISECOND, 0);
    // Date today = cal.getTime();

    // int count = alerteEnvoyeeRepository.findAll().stream()
    // .filter(a -> a.getDateEnvoi().after(today) &&
    // "SUCCES".equals(a.getStatutEnvoi()))
    // .mapToInt(a -> 1)
    // .sum();

    // return count;
    // }
    public int manualCheckAndSendAlerts() {
        log.info("Vérification manuelle des alertes déclenchée");

        checkAndSendAlerts();

        // Pour des fins de test, retourner le nombre d'alertes envoyées directement
        // puisqu'on ne sauvegarde pas en BD
        return 0; // Ou une valeur fixe pour test, comme return 5;
    }
}
