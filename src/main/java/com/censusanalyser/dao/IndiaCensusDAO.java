package com.censusanalyser.dao;

import com.censusanalyser.analyse.ConstantPaths;
import com.censusanalyser.model.IndiaCensusCSV;
import com.censusanalyser.model.IndiaStateCodeCSV;
import com.censusanalyser.model.USCensusCSV;

public class IndiaCensusDAO {

    public String state;
    public int population;
    public String stateCode;
    public double totalArea;
    public double populationDensity;

    public IndiaCensusDAO(IndiaCensusCSV indiaCensusCSV) {

        state = indiaCensusCSV.state;
        totalArea = indiaCensusCSV.areaInSqKm;
        populationDensity = indiaCensusCSV.densityPerSqKm;
        population = indiaCensusCSV.population;
    }
    public IndiaCensusDAO(IndiaStateCodeCSV indiaStateCodeCSV) {
        state = indiaStateCodeCSV.state;
        stateCode = indiaStateCodeCSV.stateCode;
    }
    public IndiaCensusDAO(USCensusCSV usCensusCSV) {
        state = usCensusCSV.state;
        totalArea = usCensusCSV.totalArea;
        population = usCensusCSV.population;
        populationDensity = usCensusCSV.populationDensity;
        stateCode = usCensusCSV.stateId;
    }

    public Object getCensusDTO(ConstantPaths.Country country) {
        if(country.equals( ConstantPaths.Country.INDIA ))
            return new IndiaCensusCSV(state,population,(int)populationDensity,(int)totalArea);
        return new USCensusCSV(state,stateCode,population,populationDensity,totalArea);
    }
}