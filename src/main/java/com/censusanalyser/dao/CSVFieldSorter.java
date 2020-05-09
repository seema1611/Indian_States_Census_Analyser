package com.censusanalyser.dao;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Map;

public class CSVFieldSorter {

    public Comparator<IndiaCensusDAO> getCurrentSort(String field) {

        Comparator<IndiaCensusDAO> comparator = null;
        switch (field) {
            case "population":
                comparator = Comparator.comparing( census -> census.population );
                break;
            case "density":
                comparator = Comparator.comparing( census -> census.populationDensity );
                break;
            case "area":
                comparator = Comparator.comparing( census -> census.totalArea );
                break;
            case "state":
                comparator = Comparator.comparing( census -> census.state );
                break;
            case "statecode":
                comparator = Comparator.comparing( census -> census.stateCode );
                break;
        }
        return comparator;
    }

    public <E> void getCensusObject(Class<E> censusClass, Map censusMap, Object censusCSV) {
        try {
            Field field = censusClass.getDeclaredField( "state" );
            Class<?> censusDAOClass = Class.forName("com.censusanalyser.dao.IndiaCensusDAO");
            Constructor<?> censusConstructor = censusDAOClass.getConstructor( censusClass );
            String value = (String) field.get( censusCSV );
            censusMap.put( value, censusConstructor.newInstance( censusCSV ) );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}