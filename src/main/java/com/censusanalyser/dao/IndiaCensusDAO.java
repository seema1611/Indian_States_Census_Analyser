package com.censusanalyser.dao;

import com.censusanalyser.model.IndiaCensusCSV;
import com.censusanalyser.model.IndiaStateCodeCSV;

public class IndiaCensusDAO {
    public String state;
    public int areaInSqKm;
    public int densityPerSqKm;
    public int population;
    public String stateCode;
    public String sortCode;

    public IndiaCensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
        population = indiaCensusCSV.population;
        sortCode=state;
    }
    public IndiaCensusDAO(IndiaStateCodeCSV indiaStateCodeCSV) {
        state = indiaStateCodeCSV.state;
        stateCode = indiaStateCodeCSV.stateCode;
        sortCode=stateCode;
    }
}
