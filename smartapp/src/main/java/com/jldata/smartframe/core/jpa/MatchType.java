package com.jldata.smartframe.core.jpa;

/**
 * MatchType.
 * @Author ljl
 */
public enum MatchType {
    /** equals. */
    EQ,
    /** %value% */
    CONTAIN,
    /** %value */
    START,
    /** value% */
    END,
    /** less than. */
    LT,
    /** greater than. */
    GT,
    /** less equals. */
    LE,
    /** greater equals. */
    GE,
    /** in. */
    IN,
    /** NOT. */
    NOT,
    /** IS NULL. */
    INL,
    /** NOT NULL. */
    NNL,
    /** unknown. */
    UNKNOWN;
}
