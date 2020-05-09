package com.censusanalyser.analyse;

import com.censusanalyser.dao.CSVFieldSorter;
import com.censusanalyser.dao.IndiaCensusDAO;
import com.censusanalyser.exception.CensusAnalyserException;
import com.censusanalyser.model.IndiaCensusCSV;
import com.censusanalyser.model.USCensusCSV;

import com.google.gson.Gson;

import java.util.*;

public class CensusAnalyser {
    List<IndiaCensusDAO> censusList;
    Map<String, IndiaCensusDAO> censusMap;

    public CensusAnalyser() {

    }

    public int loadIndiaCensusData(String... csvFilePath) throws CensusAnalyserException {
        censusMap=new  CensusLoader().loadCensusData( IndiaCensusCSV.class,csvFilePath );
        return censusMap.size();
    }

    public int loadUSCodeData(String csvFilePath) throws CensusAnalyserException {
        censusMap= new CensusLoader().loadCensusData(  USCensusCSV.class ,csvFilePath);
        return censusMap.size();
    }


    public String getStateWiseCensusData(String fieldType) throws CensusAnalyserException {
        if (censusMap == null || censusMap.size() == 0) {
            throw new CensusAnalyserException( "No Census Data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA );
        }
        censusList = new ArrayList( censusMap.values() );
        Comparator<IndiaCensusDAO> censusComparator = new CSVFieldSorter().getCurrentSort( fieldType );
        this.sort( censusComparator );
        String sortedStateCensusJson = new Gson().toJson( this.censusList );
        return sortedStateCensusJson;
    }

    private void sort(Comparator<IndiaCensusDAO> censusComparator) {
        for (int i = 0; i < censusList.size() - 1; i++) {
            for (int j = 0; j < censusList.size() - i - 1; j++) {
                IndiaCensusDAO census1 = censusList.get( j );
                IndiaCensusDAO census2 = censusList.get( j + 1 );
                if (censusComparator.compare( census2, census1 ) > 0) {
                    censusList.set( j, census2 );
                    censusList.set( j + 1, census1 );
                }
            }
        }
    }
}