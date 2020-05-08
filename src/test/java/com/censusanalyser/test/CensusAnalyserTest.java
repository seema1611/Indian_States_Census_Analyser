package com.censusanalyser.test;

import com.censusanalyser.analyse.CensusAnalyser;
import com.censusanalyser.exception.CensusAnalyserException;
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
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
}