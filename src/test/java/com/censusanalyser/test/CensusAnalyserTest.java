package com.censusanalyser.test;

import com.censusanalyser.analyse.CensusAnalyser;
import com.censusanalyser.analyse.ConstantPaths;
import com.censusanalyser.exception.CSVBuilderException;
import com.censusanalyser.exception.CensusAnalyserException;
import com.censusanalyser.model.IndiaCensusCSV;
import com.censusanalyser.model.IndiaStateCodeCSV;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.censusanalyser.analyse.ConstantPaths.*;

public class CensusAnalyserTest {
    //Objects
    CensusAnalyser censusAnalyser;

    @Before
    public void initialize() {
        censusAnalyser = new CensusAnalyser();
    }

    //UC-1
    //TC-1.1
    @Test
    public void givenFilePath_WhenNoOfRecordMatches_ShouldReturnTrue() {
        try {
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,numOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    //TC-1.2
    @Test
    public void givenStateCensusData_WhenWithWrongFile_ShouldThrowException() {
        try
        {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    //TC-1.3
    @Test
    public void givenStateCensusData_WhenWithWrongType_ShouldThrowException() {
        try
        {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    //TC-1.4
    @Test
    public void givenStateCensusData_WhenWithWrongDelimiter_ShouldThrowException() {
        try
        {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUES,e.type);
        }
    }

    //TC-1.5
    @Test
    public void givenStateCensusData_WhenWithWrongHeader_ShouldThrowException() {
        try
        {
            censusAnalyser.loadIndiaCensusData(WRONG_HEADER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUES,e.type);
        }
    }

    //UseCase-2
    //TC-2.1
    @Test
    public void givenFilePathOfStateCode_WhenNoOfRecordMatches_ThenReturnTrue() {
        try
        {
            int numOfStateCode = censusAnalyser.loadIndianStateCode(INDIA_STATE_CODE_FILE_PATH);
            Assert.assertEquals("37",numOfStateCode);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    //TC-2.2
    @Test
    public void givenIndiaStateCodeData_WithWrongFile_ShouldThrowException() {
        try
        {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect( CensusAnalyserException.class );
            censusAnalyser.loadIndianStateCode(WRONG_CODE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals( CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type );
        }
    }

    //TC-2.3
    @Test
    public void givenIndianStateCode_WithWrongFileType_Should_ReturnException() {
        try
        {
            censusAnalyser.loadIndianStateCode(WRONG_CODE_CSV_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type );
        }
    }

    //TC-2.4
    @Test
    public void givenIndianStateCodeData_WithIncorrectDelimiter_Should_ReturnException() {
        try
        {
            censusAnalyser.loadIndianStateCode(WRONG_DELIMITER_CODE_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals( CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUES, e.type );
        }
    }

    //TC-2.5
    @Test
    public void givenIndianStateCodeData_WithIncorrectHeader_Should_ReturnException() {
        try
        {
            censusAnalyser.loadIndianStateCode(WRONG_HEADER_CODE_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals( CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUES, e.type );
        }
    }

    //UC-3
    //TC-3.1
    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnStartSortedResult() throws CensusAnalyserException {
        censusAnalyser.loadIndiaCensusData( ConstantPaths.INDIA_CENSUS_CSV_FILE_PATH );
        String sortedCensusData = censusAnalyser.getStateWiseCensusData();
        IndiaCensusCSV[] censusCSV = new Gson().fromJson( sortedCensusData, IndiaCensusCSV[].class );
        Assert.assertEquals( "Andhra Pradesh", censusCSV[0].state );
    }

    //TC-3.2
    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnEndSortedResult() throws CensusAnalyserException {
        censusAnalyser.loadIndiaCensusData( ConstantPaths.INDIA_CENSUS_CSV_FILE_PATH );
        String sortedCensusData = censusAnalyser.getStateWiseCensusData();
        IndiaCensusCSV[] censusCSV = new Gson().fromJson( sortedCensusData, IndiaCensusCSV[].class );
        Assert.assertEquals( "West Bengal", censusCSV[censusCSV.length - 1].state );
    }

    //UC-4
    //TC-4.1
    @Test
    public void givenIndianStateCodeData_WhenSortedOnState_ShouldReturnStartSortedResult() throws CensusAnalyserException {
        censusAnalyser.loadIndianStateCode( ConstantPaths.INDIA_STATE_CODE_FILE_PATH );
        String sortedCensusData = censusAnalyser.getStateWiseCensusData();
        IndiaStateCodeCSV[] censusCSV = new Gson().fromJson( sortedCensusData, IndiaStateCodeCSV[].class );
        Assert.assertEquals( "Andaman and Nicobar Islands", censusCSV[0].state );
    }

    //TC-4.2
    @Test
    public void givenIndianStateCodeData_WhenSortedOnState_ShouldReturnEndSortedResult() throws CensusAnalyserException {
        censusAnalyser.loadIndianStateCode( ConstantPaths.INDIA_STATE_CODE_FILE_PATH );
        String sortedCensusData = censusAnalyser.getStateWiseCensusData();
        IndiaStateCodeCSV[] censusCSV = new Gson().fromJson( sortedCensusData, IndiaStateCodeCSV[].class );
        Assert.assertEquals( "West Bengal", censusCSV[censusCSV.length - 1].state );
    }

    //UC-5
    //TC-5.1
    @Test
    public void givenIndianCensusCode_WhenSorted_ThenShouldReturnSortedPopulation() throws CensusAnalyserException {
        censusAnalyser.loadIndiaCensusData( ConstantPaths.INDIA_CENSUS_CSV_FILE_PATH, "population" );
        String sortedCensusData = censusAnalyser.getStateWiseCensusDataIntegerValues();
        IndiaCensusCSV[] censusCSV = new Gson().fromJson( sortedCensusData, IndiaCensusCSV[].class );
        Assert.assertEquals( "Uttar Pradesh", censusCSV[0].state );
    }

    //UC-6
    //TC-6.1
    @Test
    public void givenIndianCensusData_WhenSorted_ThenShouldReturnSortedDensity() throws CensusAnalyserException {
        censusAnalyser.loadIndiaCensusData( ConstantPaths.INDIA_CENSUS_CSV_FILE_PATH, "density" );
        String sortedCensusData = censusAnalyser.getStateWiseCensusDataIntegerValues();
        IndiaCensusCSV[] censusCSV = new Gson().fromJson( sortedCensusData, IndiaCensusCSV[].class );
        Assert.assertEquals( "Bihar", censusCSV[0].state );
    }

    //UC-7
    //TC-7.1
    @Test
    public void givenIndianCensusData_WhenSorted_ThenShouldReturnSortedArea() throws CensusAnalyserException {
        censusAnalyser.loadIndiaCensusData( ConstantPaths.INDIA_CENSUS_CSV_FILE_PATH, "area" );
        String sortedCensusData = censusAnalyser.getStateWiseCensusDataIntegerValues();
        IndiaCensusCSV[] censusCSV = new Gson().fromJson( sortedCensusData, IndiaCensusCSV[].class );
        Assert.assertEquals( "Rajasthan", censusCSV[0].state );
    }
}