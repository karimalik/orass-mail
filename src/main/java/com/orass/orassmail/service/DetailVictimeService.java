package com.orass.orassmail.service;

import com.orass.orassmail.model.DetailVictime;
import com.orass.orassmail.repository.DetailVictimeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailVictimeService {

    @Autowired
    private DetailVictimeRepository detailVictimeRepository;

    public List<DetailVictime> getSinistresNonRegles() {
        return detailVictimeRepository.findSinistresNonRegles();
    }
}
