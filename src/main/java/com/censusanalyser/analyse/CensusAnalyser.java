package com.censusanalyser.analyse;

import com.censusanalyser.exception.CSVBuilderException;
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
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<IndiaCensusCSV> censusCSVList = null;
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader( Paths.get( csvFilePath ) );
            ICSVBuilder csvBuilderInterface = CSVBuilderFactory.createCSVBuilder();
            censusCSVList = csvBuilderInterface.getCSVFileList( reader, IndiaCensusCSV.class );
            return censusCSVList.size();
        } catch (RuntimeException e) {
            throw new CensusAnalyserException( e.getMessage(),
                    CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUES );
        } catch (IOException | CSVBuilderException e) {
            throw new CensusAnalyserException( e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM );
        }
    }

    public int loadIndianStateCode(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader( Paths.get( csvFilePath ) );
            ICSVBuilder csvBuilderInterface = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> censusCSVIterator = csvBuilderInterface.getCSVFileIterator( reader, IndiaStateCodeCSV.class );
            return getCount( censusCSVIterator );
        } catch (RuntimeException e) {
            throw new CensusAnalyserException( e.getMessage(),
                    CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUES );
        } catch (IOException | CSVBuilderException e) {
            throw new CensusAnalyserException( e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM );
        }
    }
    public <E> int getCount(Iterator<E> censusCSVIterator) {
        Iterable<E> csvIterable = () -> censusCSVIterator;
        return (int) StreamSupport.stream( csvIterable.spliterator(), false ).count();
    }

    public String getStateWiseCensusData() throws CensusAnalyserException {
        if (censusCSVList == null || censusCSVList.size() == 0) {
            throw new CensusAnalyserException( "No Census Data",
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing( census -> census.state );
        this.sort( censusComparator );
        String sortedStateCensusJson = new Gson().toJson( censusCSVList );
        return sortedStateCensusJson;
    }
    private void sort(Comparator<IndiaCensusCSV> censusComparator) {
        for (int i = 0; i < censusCSVList.size() - 1; i++) {
            for (int j = 0; j < censusCSVList.size() - i - 1; j++) {
                IndiaCensusCSV census1 = censusCSVList.get( j );
                IndiaCensusCSV census2 = censusCSVList.get( j + 1 );
                if (censusComparator.compare( census1, census2 ) > 0) {
                    censusCSVList.set( j, census2 );
                    censusCSVList.set( j + 1, census1 );
                }
            }
        }
    }
}