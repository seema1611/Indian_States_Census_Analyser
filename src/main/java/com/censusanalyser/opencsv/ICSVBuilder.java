package com.censusanalyser.opencsv;

import com.censusanalyser.exception.CSVBuilderException;

import java.io.Reader;
import java.util.Iterator;

public interface ICSVBuilder<E> {
    Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) throws CSVBuilderException;
}

