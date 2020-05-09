package com.censusanalyser.analyse;

import com.censusanalyser.dao.IndiaCensusDAO;
import com.censusanalyser.exception.CensusAnalyserException;
import com.censusanalyser.model.IndiaCensusCSV;
import com.censusanalyser.model.IndiaStateCodeCSV;
import com.censusanalyser.opencsv.CSVBuilderFactory;
import com.censusanalyser.opencsv.ICSVBuilder;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class CensusAnalyser {
    HashMap<String, IndiaCensusDAO> censusMap;
    List<IndiaCensusDAO> censusList = null;

    public CensusAnalyser() {
        this.censusMap = new HashMap<>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader( Paths.get( csvFilePath ) );
            ICSVBuilder csvBuilderInterface = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> csvFileIterator = csvBuilderInterface.getCSVFileIterator( reader, IndiaCensusCSV.class );
            while (csvFileIterator.hasNext()) {
                IndiaCensusCSV indiaCensusCSV = csvFileIterator.next();
                censusMap.put( indiaCensusCSV.state, new IndiaCensusDAO( indiaCensusCSV ) );
            }
            censusList = new ArrayList( censusMap.values() );
            return censusList.size();
        } catch (RuntimeException e) {
            throw new CensusAnalyserException( e.getMessage(),
                    CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUES );
        } catch (IOException e) {
            throw new CensusAnalyserException( e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM );
        }
    }

    public int loadIndianStateCode(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader( Paths.get( csvFilePath ) );
            ICSVBuilder csvBuilderInterface = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> csvFileIterator = csvBuilderInterface.getCSVFileIterator( reader, IndiaStateCodeCSV.class );
            while (csvFileIterator.hasNext()) {
                IndiaStateCodeCSV indianStateCSV = csvFileIterator.next();
                censusMap.put( indianStateCSV.state, new IndiaCensusDAO( indianStateCSV ) );
            }
            censusList = new ArrayList( censusMap.values() );
            return censusList.size();
        } catch (RuntimeException e) {
            throw new CensusAnalyserException( e.getMessage(),
                    CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUES );
        } catch (IOException e) {
            throw new CensusAnalyserException( e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM );
        }
    }

    public String getStateWiseCensusData() throws CensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException( "No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA );
        }
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing( census -> census.sortCode );
        this.sort( censusComparator );
        String sortedStateCensusJson = new Gson().toJson( this.censusList );
        return sortedStateCensusJson;
    }

    private void sort(Comparator<IndiaCensusDAO> censusComparator) {
        for (int i = 0; i < censusList.size() - 1; i++) {
            for (int j = 0; j < censusList.size() - i - 1; j++) {
                IndiaCensusDAO census1 = censusList.get( j );
                IndiaCensusDAO census2 = censusList.get( j + 1 );
                if (censusComparator.compare( census1, census2 ) > 0) {
                    censusList.set( j, census2 );
                    censusList.set( j + 1, census1 );
                }
            }
        }
    }
}