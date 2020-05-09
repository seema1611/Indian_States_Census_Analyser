package com.censusanalyser.test;

import com.censusanalyser.analyse.CensusAnalyser;
import com.censusanalyser.analyse.ConstantPaths;
import com.censusanalyser.exception.CensusAnalyserException;
import com.censusanalyser.model.IndiaCensusCSV;
import com.censusanalyser.model.IndiaStateCodeCSV;
import com.censusanalyser.model.USCensusCSV;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Collections;

import static com.censusanalyser.analyse.ConstantPaths.*;

public class CensusAnalyserTest {

    //UC-1
    //TC-1.1
    @Test
    public void givenFilePath_WhenNoOfRecordMatches_ShouldReturnTrue() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser( Country.INDIA );
            int numOfRecords = censusAnalyser.loadCensusData( CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_FILE_PATH);
            Assert.assertEquals( 29, numOfRecords );
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    //TC-1.2
    @Test
    public void givenStateCensusData_WhenWithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser( Country.INDIA );
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect( CensusAnalyserException.class );
            censusAnalyser.loadCensusData( CensusAnalyser.Country.INDIA, WRONG_CSV_FILE_PATH );
        } catch (CensusAnalyserException e) {
            Assert.assertEquals( CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type );
        }
    }

    //TC-1.3
    @Test
    public void givenStateCensusData_WhenWithWrongType_ShouldThrowException() {
        try
        {
            CensusAnalyser censusAnalyser = new CensusAnalyser( Country.INDIA );
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect( CensusAnalyserException.class );
            censusAnalyser.loadCensusData( CensusAnalyser.Country.INDIA, WRONG_CSV_FILE_TYPE );
        } catch (CensusAnalyserException e) {
            Assert.assertEquals( CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type );
        }
    }

    //TC-1.4
    @Test
    public void givenStateCensusData_WhenWithWrongDelimiter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser( Country.INDIA );
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect( CensusAnalyserException.class );
            censusAnalyser.loadCensusData( CensusAnalyser.Country.INDIA, WRONG_DELIMITER );
        } catch (CensusAnalyserException e) {
            Assert.assertEquals( CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUES, e.type );
        }
    }

    //TC-1.5
    @Test
    public void givenStateCensusData_WhenWithWrongHeader_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser( Country.INDIA );
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect( CensusAnalyserException.class );
            censusAnalyser.loadCensusData( CensusAnalyser.Country.INDIA, WRONG_HEADER );
        } catch (CensusAnalyserException e) {
            Assert.assertEquals( CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUES, e.type );
        }
    }

    //UseCase-2
    //TC-2.1
    @Test
    public void givenFilePathOfStateCode_WhenNoOfRecordMatches_ThenReturnTrue() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser( Country.INDIA );
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect( CensusAnalyserException.class );
            int numOfRecords = censusAnalyser.loadCensusData( CensusAnalyser.Country.INDIA, INDIA_STATE_CODE_FILE_PATH );
            Assert.assertEquals( 36, numOfRecords );
        } catch (CensusAnalyserException e) {
        }
    }

    //TC-2.2
    @Test
    public void givenIndiaStateCodeData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser( Country.INDIA );
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect( CensusAnalyserException.class );
            censusAnalyser.loadCensusData( CensusAnalyser.Country.INDIA, WRONG_CODE_CSV_FILE_PATH );
        } catch (CensusAnalyserException e) {
            Assert.assertEquals( CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type );
        }
    }

    //TC-2.3
    @Test
    public void givenIndianStateCode_WithWrongFileType_Should_ReturnException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser( Country.INDIA );
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect( CensusAnalyserException.class );
            censusAnalyser.loadCensusData( CensusAnalyser.Country.INDIA, WRONG_CODE_CSV_FILE_TYPE );
        } catch (CensusAnalyserException e) {
            Assert.assertEquals( CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type );
        }
    }

    //TC-2.4
    @Test
    public void givenIndianStateCodeData_WithIncorrectDelimiter_Should_ReturnException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser( Country.INDIA );
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect( CensusAnalyserException.class );
            censusAnalyser.loadCensusData( CensusAnalyser.Country.INDIA, WRONG_DELIMITER_CODE_FILE );
        } catch (CensusAnalyserException e) {
            Assert.assertEquals( CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUES, e.type );
        }
    }

    //TC-2.5
    @Test
    public void givenIndianStateCodeData_WithIncorrectHeader_Should_ReturnException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser( Country.INDIA );
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect( CensusAnalyserException.class );
            censusAnalyser.loadCensusData( CensusAnalyser.Country.INDIA, WRONG_HEADER_CODE_FILE );
        } catch (CensusAnalyserException e) {
            Assert.assertEquals( CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUES, e.type );
        }
    }

    //UC-3
    //TC-3.1
    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnStartSortedResult() throws CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser( Country.INDIA );
        censusAnalyser.loadCensusData( CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_FILE_PATH );
        String sortedCensusData = censusAnalyser.getStateWiseCensusData( "state" );
        IndiaCensusCSV[] censusCSV = new Gson().fromJson( sortedCensusData, IndiaCensusCSV[].class );
        Collections.reverse( Arrays.asList( censusCSV ) );
        Assert.assertEquals( "Andhra Pradesh", censusCSV[0].state );
    }

    //TC-3.2
    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnEndSortedResult() throws CensusAnalyserException {

        CensusAnalyser censusAnalyser = new CensusAnalyser( Country.INDIA );
        censusAnalyser.loadCensusData( CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_FILE_PATH );
        String sortedCensusData = censusAnalyser.getStateWiseCensusData( "state" );
        IndiaCensusCSV[] censusCSV = new Gson().fromJson( sortedCensusData, IndiaCensusCSV[].class );
        Collections.reverse( Arrays.asList( censusCSV ) );
        Assert.assertEquals( "West Bengal", censusCSV[censusCSV.length - 1].state );
    }

    //UC-5
    //TC-5.1
    @Test
    public void givenIndianCensusCode_WhenSorted_ThenShouldReturnSortedPopulation() throws CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser( Country.INDIA );
        censusAnalyser.loadCensusData( CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseCensusData( "population" );
        IndiaCensusCSV[] censusCSV = new Gson().fromJson( sortedCensusData, IndiaCensusCSV[].class );
        Assert.assertEquals( "Uttar Pradesh", censusCSV[0].state );
    }

    //UC-6
    //TC-6.1
    @Test
    public void givenIndianCensusData_WhenSorted_ThenShouldReturnSortedDensity() throws CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser( Country.INDIA );
        censusAnalyser.loadCensusData( CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseCensusData( "density" );
        IndiaCensusCSV[] censusCSV = new Gson().fromJson( sortedCensusData, IndiaCensusCSV[].class );
        Assert.assertEquals( "Bihar", censusCSV[0].state );
    }


    //UC-8
    //TC-8.1
    @Test
    public void givenUSCensus_CSVFile_ReturnsCorrectRecords() throws CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser(Country.US);
        int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        Assert.assertEquals(51, numOfRecords);
    }
}