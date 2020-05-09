package com.censusanalyser.analyse;

import com.censusanalyser.dao.CSVFieldSorter;
import com.censusanalyser.dao.IndiaCensusDAO;
import com.censusanalyser.exception.CensusAnalyserException;
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

public class CensusLoader {
    Map<String, IndiaCensusDAO> censusMap = new HashMap<>();

    public <E> Map<String, IndiaCensusDAO> loadCensusData(Class<E> censusClass,
                                                     String... csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader( Paths.get( csvFilePath[0] ) );
            ICSVBuilder csvBuilderInterface = CSVBuilderFactory.createCSVBuilder();
            List<E> csvList = csvBuilderInterface.getCSVFileList( reader, censusClass );
            csvList.forEach( (censusCSV) -> new CSVFieldSorter().getCensusObject( censusClass,
                    censusMap, censusCSV ) );
            if (csvFilePath.length == 1) return censusMap;
            this.loadIndianStateCode( censusMap, csvFilePath[1] );
            return censusMap;
        } catch (RuntimeException e) {
            throw new CensusAnalyserException( e.getMessage(),
                    CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUES );
        } catch (IOException e) {
            throw new CensusAnalyserException( e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM );
        }
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
