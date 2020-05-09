package com.censusanalyser.model;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV {
    @CsvBindByName(column = "StateId", required = true)
    public String stateId;

    @CsvBindByName(column = "State", required = true)
    public String state;

    @CsvBindByName(column = "Population", required = true)
    public int population;

    @CsvBindByName(column = "TotalArea", required = true)
    public double totalArea;

    @CsvBindByName(column = "PopulationDensity", required = true)
    public double populationDensity;

    public USCensusCSV() {
    }

    public USCensusCSV(String state, String stateCode, int population, double populationDensity, double totalArea) {
        this.state = state;
        this.population = population;
        this.stateId = stateCode;
        this.populationDensity = populationDensity;
        this.totalArea = totalArea;
    }

    @Override
    public String toString() {
        return "USCensusCSV{" +
                "state='" + state + '\'' +
                ", populationDensity=" + populationDensity +
                ", totalArea=" + totalArea +
                ", population=" + population +
                ", stateId='" + stateId + '\'' +
                '}';
    }
}