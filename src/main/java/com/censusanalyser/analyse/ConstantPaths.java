package com.censusanalyser.analyse;

public class ConstantPaths {
    //Indian Data
    public static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    public static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    public static final String WRONG_CSV_FILE_TYPE = "./src/main/resources/IndiaStateCensusData.txt";
    public static final String WRONG_DELIMITER = "./src/test/resources/WrongData.csv";
    public static final String WRONG_HEADER = "./src/test/resources/WrongHeader.csv";

    //Indian Code
    public static final String INDIA_STATE_CODE_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    public static final String WRONG_CODE_CSV_FILE_PATH="./src/main/resources/IndiaStateCode.csv";
    public static final String WRONG_CODE_CSV_FILE_TYPE = "./src/test/resources/IndiaStateCode.txt";
    public static final String WRONG_DELIMITER_CODE_FILE="./src/test/resources/WrongCodeDelimiter.csv";
    public static final String WRONG_HEADER_CODE_FILE="./src/test/resources/WrongCodeHeader.csv";

    //US Data
    public static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";

    public enum Country {
        INDIA, US
    }
}