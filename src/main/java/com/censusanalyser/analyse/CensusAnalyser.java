package com.censusanalyser.analyse;

import com.censusanalyser.adapter.CensusAdapterFactory;
import com.censusanalyser.dao.IndiaCensusDAO;
import com.censusanalyser.exception.CensusAnalyserException;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser extends ConstantPaths {
    public Country country;

    public CensusAnalyser(Country country) {
        this.country = country;
    }

    Map<String, IndiaCensusDAO> censusMap;

    public int loadCensusData(ConstantPaths.Country country,String... csvFilePath) throws CensusAnalyserException {
        censusMap = CensusAdapterFactory.getCensusData(country, csvFilePath);
        return censusMap.size();
    }

    public String getStateWiseCensusData(String fieldType) throws CensusAnalyserException {
        if (censusMap == null || censusMap.size() == 0) {
            throw new CensusAnalyserException("No Census Data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDAO> censusComparator = new CensusAdapterFactory().getCurrentSort(fieldType);
        List censusDTO = censusMap.values().stream()
                .sorted(censusComparator.reversed())
                .map(indiaCensusDAO -> indiaCensusDAO.getCensusDTO(country))
                .collect(Collectors.toList());
        String sortedStateCensusJson = new Gson().toJson(censusDTO);
        return sortedStateCensusJson;
    }
}