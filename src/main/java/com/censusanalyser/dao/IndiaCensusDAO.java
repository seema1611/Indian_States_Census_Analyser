package com.censusanalyser.dao;

import com.censusanalyser.model.IndiaCensusCSV;
import com.censusanalyser.model.IndiaStateCodeCSV;

public class IndiaCensusDAO {

    public String state;
    public Integer areaInSqKm;
    public Integer densityPerSqKm;
    public Integer population;
    public String stateCode;

    public IndiaCensusDAO(IndiaCensusCSV indiaCensusCSV) {

        state = indiaCensusCSV.state;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
        population = indiaCensusCSV.population;
    }
    public IndiaCensusDAO(IndiaStateCodeCSV indiaStateCodeCSV) {
        state = indiaStateCodeCSV.state;
        stateCode = indiaStateCodeCSV.stateCode;
    }
}