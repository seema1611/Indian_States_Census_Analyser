package com.censusanalyser.adapter;

import com.censusanalyser.dao.IndiaCensusDAO;
import com.censusanalyser.exception.CensusAnalyserException;
import com.censusanalyser.opencsv.CSVBuilderFactory;
import com.censusanalyser.opencsv.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CensusAdapter {
    public abstract Map<String, IndiaCensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException;

    public <E> Map<String, IndiaCensusDAO> loadCensusData(Class<E> censusClass,
                                                     String csvFilePath) throws CensusAnalyserException {
        Map<String, IndiaCensusDAO> censusMap = new HashMap<>();
        try {
            Reader reader = Files.newBufferedReader( Paths.get( csvFilePath ) );
            ICSVBuilder csvBuilderInterface = CSVBuilderFactory.createCSVBuilder();
            List<E> csvList = csvBuilderInterface.getCSVFileList( reader, censusClass );
            csvList.forEach( (censusCSV) -> new CensusAdapterFactory().getCensusObject( censusClass,
                    censusMap, censusCSV ) );
            return censusMap;
        } catch (RuntimeException e) {
            throw new CensusAnalyserException( e.getMessage(),
                    CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUES );
        } catch (IOException e) {
            throw new CensusAnalyserException( e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM );
        }
    }
}