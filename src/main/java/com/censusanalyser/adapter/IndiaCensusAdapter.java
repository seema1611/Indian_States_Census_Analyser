package com.censusanalyser.adapter;

import com.censusanalyser.dao.IndiaCensusDAO;
import com.censusanalyser.exception.CensusAnalyserException;
import com.censusanalyser.model.IndiaCensusCSV;
import com.censusanalyser.model.IndiaStateCodeCSV;
import com.censusanalyser.opencsv.CSVBuilderFactory;
import com.censusanalyser.opencsv.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndiaCensusAdapter extends CensusAdapter {
    Map<String, IndiaCensusDAO> censusMap = new HashMap<>();

    @Override
    public Map<String, IndiaCensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        Map<String, IndiaCensusDAO> censusMap = super.loadCensusData( IndiaCensusCSV.class, csvFilePath[0] );
        this.loadIndianStateCode( censusMap, csvFilePath[1] );
        return censusMap;
    }

    public int loadIndianStateCode(Map<String, IndiaCensusDAO> censusMap,
                                       String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader( Paths.get( csvFilePath ) );
            ICSVBuilder csvBuilderInterface = CSVBuilderFactory.createCSVBuilder();
            List<IndiaStateCodeCSV> csvList = csvBuilderInterface.getCSVFileList
                    ( reader, IndiaStateCodeCSV.class );
            csvList.stream().filter( stateCSV -> this.censusMap.get( stateCSV.state ) != null )
                    .forEach( stateCSV -> this.censusMap.get( stateCSV.state ).stateCode = stateCSV.stateCode );
            return this.censusMap.size();
        } catch (RuntimeException e) {
            throw new CensusAnalyserException( e.getMessage(),
                    CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUES );
        } catch (IOException e) {
            throw new CensusAnalyserException( e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM );
        }
    }
}
