package com.feignz.candidate_vacancy_api.util;

public class CriteriaConstants {

    public static final String AGE = "AGE";
    public static final String GENDER = "GENDER";
    public static final String SALARY = "SALARY";

    public static boolean isRequired(String type) {
        if (type == null)
            return false;

        return type.equals(AGE) || type.equals(GENDER) || type.equals(SALARY);
    }
}
