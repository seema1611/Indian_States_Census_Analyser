package com.censusanalyser.adapter;

import com.censusanalyser.dao.IndiaCensusDAO;
import com.censusanalyser.exception.CensusAnalyserException;
import com.censusanalyser.model.USCensusCSV;

import java.util.Map;

class USCensusAdapter extends CensusAdapter {
    @Override
    public Map<String, IndiaCensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        return super.loadCensusData( USCensusCSV.class,csvFilePath[0] );
    }
}
