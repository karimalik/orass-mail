package com.orass.orassmail.model;

import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.IdClass;
import jakarta.persistence.Transient;

// import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "V_SMS_DETAIL_VICTIME")
@IdClass(DetailVictimeId.class)
public class DetailVictime {
    @Id
    @Column(name = "CODEINTE")
    private String codeInte;

    @Id
    @Column(name = "EXERSINI")
    private String exerSini;

    @Id
    @Column(name = "NUMESINI")
    private String numeSini;

    @Id
    @Column(name = "NUMETIER")
    private String numeTier;

    @Column(name = "DATESURV")
    private Date dateSurv;

    @Column(name = "LIEUSINI")
    private String lieuSini;

    @Column(name = "NOM_TIER")
    private String nomTier;

    @Column(name = "LIBEVEGA")
    private String libeVega;

    @Column(name = "RELIQUAT")
    private Double reliquat;

    @Column(name = "DATE_EVA")
    private Date dateEva;

    @Column(name = "NBR_JOUR")
    private Integer nbrJour;

    @Column(name = "TELEPHONE")
    private String telephone;

    @Column(name = "CREE_PAR")
    private String creePar;

    // @Column(name = "EMAIL")
    @Transient
    private String email;
}
