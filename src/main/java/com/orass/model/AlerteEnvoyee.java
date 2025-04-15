package com.orass.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ALERTE_ENVOYEE")
public class AlerteEnvoyee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alerte_seq")
    @SequenceGenerator(name = "alerte_seq", sequenceName = "SEQ_ALERTE", allocationSize = 1)
    private Long id;
    
    @Column(name = "CODE_INTE")
    private String codeInte;
    
    @Column(name = "EXER_SINI")
    private String exerSini;
    
    @Column(name = "NUME_SINI")
    private String numeSini;
    
    @Column(name = "NUME_TIER")
    private String numeTier;
    
    @Column(name = "EMAIL_DESTINATAIRE")
    private String emailDestinataire;
    
    @Column(name = "DATE_ENVOI")
    private Date dateEnvoi;
    
    @Column(name = "TYPE_ALERTE")
    private String typeAlerte;
    
    @Column(name = "STATUT_ENVOI")
    private String statutEnvoi;

    public AlerteEnvoyee(DetailVictime detailVictime, String typeAlerte, String statutEnvoi) {
        this.codeInte = detailVictime.getCodeInte();
        this.exerSini = detailVictime.getExerSini();
        this.numeSini = detailVictime.getNumeSini();
        this.numeTier = detailVictime.getNumeTier();
        this.emailDestinataire = detailVictime.getEmail();
        this.dateEnvoi = new Date();
        this.typeAlerte = typeAlerte;
        this.statutEnvoi = statutEnvoi;
    }
}
