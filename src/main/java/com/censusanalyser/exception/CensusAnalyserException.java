package com.censusanalyser.exception;

public class CensusAnalyserException extends Exception {

    public enum ExceptionType {
        CENSUS_FILE_PROBLEM,CSV_FILE_INTERNAL_ISSUES,NO_CENSUS_DATA,WRONG_CLASS, NO_SUCH_CLASS
    }

    public ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super( message );
        this.type = type;
    }
}