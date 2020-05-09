package com.censusanalyser.dao;

import com.censusanalyser.model.IndiaCensusCSV;
import com.censusanalyser.model.IndiaStateCodeCSV;

public class IndiaCensusDAO {

    public String state;
    public Integer areaInSqKm;
    public Integer densityPerSqKm;
    public Integer population;
    public String stateCode;
    public Integer fieldInt;
    public String fieldString;

    public IndiaCensusDAO(IndiaCensusCSV indiaCensusCSV, String fieldName) {

        state = indiaCensusCSV.state;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
        population = indiaCensusCSV.population;

        if (fieldName.equals( "state" )) {
            fieldString = state;
        }
        if (fieldName.equals( "area" )) {
            fieldInt = areaInSqKm;
        }
        if (fieldName.equals( "density" )) {
            fieldInt = densityPerSqKm;
        }
        if (fieldName.equals( "population" )) {
            fieldInt = population;
        }
    }
    public IndiaCensusDAO(IndiaStateCodeCSV indiaStateCodeCSV) {
        state = indiaStateCodeCSV.state;
        stateCode = indiaStateCodeCSV.stateCode;
        fieldString = stateCode;
    }
}