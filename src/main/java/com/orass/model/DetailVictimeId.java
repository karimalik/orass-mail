package com.orass.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class DetailVictimeId implements Serializable {
    private String codeInte;
    private String exerSini;
    private String numeSini;
    private String numeTier;
}
