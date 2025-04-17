package com.orass.orassmail.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import com.orass.orassmail.model.DetailVictime;

@Service
@Slf4j
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public boolean sendSimpleAlert(String to, String subject, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
            log.info("Email envoyé à : {}", to);
            return true;
        } catch (MailException e) {
            log.error("Erreur lors de l'envoi de l'email à {} : {}", to, e.getMessage());
            return false;
        }
    }

    public boolean sendVictimeAlert(DetailVictime victime) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(victime.getEmail());
            helper.setSubject("Alerte ORASS - Dossier sinistre en attente");

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.FRANCE);

            String content = "<html>" +
                    "<head>" +
                    "    <style>" +
                    "        body { font-family: Arial, sans-serif; }" +
                    "        .container { padding: 20px; }" +
                    "        .header { background-color: #4a7ba7; color: white; padding: 10px; }" +
                    "        .data-table { border-collapse: collapse; width: 100%; }" +
                    "        .data-table td, .data-table th { border: 1px solid #ddd; padding: 8px; }" +
                    "        .data-table tr:nth-child(even) { background-color: #f2f2f2; }" +
                    "        .warning { color: red; font-weight: bold; }" +
                    "    </style>" +
                    "</head>" +
                    "<body>" +
                    "    <div class='container'>" +
                    "        <div class='header'>" +
                    "            <h2>Alerte ORASS - Dossier sinistre en attente</h2>" +
                    "        </div>" +
                    "        <p>Bonjour,</p>" +
                    "        <p>Nous vous informons que le dossier sinistre suivant est en attente depuis <span class='warning'>"
                    + victime.getNbrJour() + " jours</span> :</p>" +
                    "        <table class='data-table'>" +
                    "            <tr><td><strong>Référence sinistre</strong></td><td>" + victime.getCodeInte() + "/"
                    + victime.getExerSini() + "/" + victime.getNumeSini() + "</td></tr>" +
                    "            <tr><td><strong>Date de survenance</strong></td><td>"
                    + (victime.getDateSurv() != null ? dateFormat.format(victime.getDateSurv()) : "N/A") + "</td></tr>"
                    +
                    "            <tr><td><strong>Lieu</strong></td><td>" + victime.getLieuSini() + "</td></tr>" +
                    "            <tr><td><strong>Victime</strong></td><td>" + victime.getNomTier() + " (Réf: "
                    + victime.getNumeTier() + ")</td></tr>" +
                    "            <tr><td><strong>Type</strong></td><td>" + victime.getLibeVega() + "</td></tr>" +
                    "            <tr><td><strong>Reliquat</strong></td><td>"
                    + (victime.getReliquat() != null ? numberFormat.format(victime.getReliquat()) : "N/A")
                    + "</td></tr>" +
                    "            <tr><td><strong>Dernière évaluation</strong></td><td>"
                    + (victime.getDateEva() != null ? dateFormat.format(victime.getDateEva()) : "N/A") + "</td></tr>" +
                    "        </table>" +
                    "        <p>Veuillez prendre les mesures nécessaires pour traiter ce dossier dans les meilleurs délais.</p>"
                    +
                    "        <p>Cordialement,<br>Système d'alertes ORASS</p>" +
                    "    </div>" +
                    "</body>" +
                    "</html>";

            helper.setText(content, true);
            mailSender.send(mimeMessage);
            log.info("Email d'alerte envoyé avec succès pour le sinistre {}/{}/{}, victime {}",
                    victime.getCodeInte(), victime.getExerSini(), victime.getNumeSini(), victime.getNumeTier());
            return true;
        } catch (MessagingException e) {
            log.error("Erreur lors de l'envoi de l'email d'alerte pour le sinistre {}/{}/{}, victime {} : {}",
                    victime.getCodeInte(), victime.getExerSini(), victime.getNumeSini(), victime.getNumeTier(),
                    e.getMessage());
            return false;
        }
    }

    public boolean sendSummaryAlert(String to, int totalAlerts, String detailContent) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            
            helper.setTo(to);
            helper.setSubject("ORASS - Résumé des alertes sinistres du jour");
            
            String content = 
                    "<html>" +
                    "<head>" +
                    "    <style>" +
                    "        body { font-family: Arial, sans-serif; }" +
                    "        .container { padding: 20px; }" +
                    "        .header { background-color: #4a7ba7; color: white; padding: 10px; }" +
                    "        .summary { font-size: 16px; margin: 20px 0; }" +
                    "    </style>" +
                    "</head>" +
                    "<body>" +
                    "    <div class='container'>" +
                    "        <div class='header'>" +
                    "            <h2>Résumé des alertes sinistres du jour</h2>" +
                    "        </div>" +
                    "        <p>Bonjour,</p>" +
                    "        <p class='summary'>Aujourd'hui, <strong>" + totalAlerts + " alertes</strong> ont été envoyées concernant des dossiers sinistres en attente.</p>" +
                    "        <h3>Détail par gestionnaire :</h3>" +
                    "        " + detailContent +
                    "        <p>Cordialement,<br>Système d'alertes ORASS</p>" +
                    "    </div>" +
                    "</body>" +
                    "</html>";
            
            helper.setText(content, true);
            mailSender.send(mimeMessage);
            log.info("Email de résumé envoyé avec succès à : {}", to);
            return true;
        } catch (MessagingException e) {
            log.error("Erreur lors de l'envoi de l'email de résumé à {} : {}", to, e.getMessage());
            return false;
        }
    }
}
