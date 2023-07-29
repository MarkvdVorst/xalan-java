/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * $Id$
 */
package org.apache.xpath.xs.types;

import org.apache.xpath.objects.ResultSequence;

/**
 * An XML Schema data type representation, of the xs:yearMonthDuration 
 * data type.
 * 
 * @author Mukul Gandhi <mukulg@apache.org>
 * 
 * @xsl.usage advanced
 */
public class XSYearMonthDuration extends XSDuration {

    private static final long serialVersionUID = 4448560721093003316L;
    
    private static final String XS_YEAR_MONTH_DURATION = "xs:yearMonthDuration";

    /**
     * Initializes this object, using the supplied parameters.
     * 
     * @param year        number of years in this duration of time
     * @param month       number of months in this duration of time
     * @param negative   'true' if this duration of time represents a backwards
     *                    passage through time. 'false' otherwise.
     */
    public XSYearMonthDuration(int year, int month, boolean negative) {
        super(year, month, 0, 0, 0, 0, negative);
    }
    
    /**
     * Initializes this object, to the supplied number of months.
     * 
     * @param months  number of months in the duration of time
     */
    public XSYearMonthDuration(int months) {
        this(0, Math.abs(months), months < 0);
    }

    /**
     * Initializes this object, to a duration with value zero 
     * (i.e, zero years, and zero months).
     */
    public XSYearMonthDuration() {
        this(0, 0, false);
    }
    
    /**
     * Creates a new XSYearMonthDuration object, by parsing the supplied
     * string representing the duration value.
     * 
     * @param   strVal   String representation of the duration value
     * @return           new XSYearMonthDuration object, representing the
     *                   duration of time supplied.
     */
    public static XSDuration parseYearMonthDuration(String strVal) {
        boolean negative = false;
        
        int year = 0;
        int month = 0;

        int moveAheadIndication = 0;

        String digits = "";
        
        for (int idx = 0; idx < strVal.length(); idx++) {
            char charVal = strVal.charAt(idx);

            switch (moveAheadIndication) {
            case 0:
                if (charVal == '-') {
                   negative = true;
                   moveAheadIndication = 4;
                } else if (charVal == 'P') {
                   moveAheadIndication = 5;
                }
                else {
                   return null;
                }
                break;
            case 4:
                if (charVal == 'P') {
                   moveAheadIndication = 5;
                }
                else {
                   return null;
                }
                break;
            case 5:
                if ('0' <= charVal && charVal <= '9') {
                   digits += charVal;
                }
                else if (charVal == 'Y') {
                    if (digits.length() == 0) {
                       return null;
                    }
                    year = Integer.parseInt(digits);
                    digits = "";
                    moveAheadIndication = 6;
                } else if (charVal == 'M') {
                    if (digits.length() == 0) {
                       return null;
                    }
                    month = Integer.parseInt(digits);
                    moveAheadIndication = 7;
                } else {
                    return null;
                }
                break;
            case 6:
               if ('0' <= charVal && charVal <= '9') {
                  digits += charVal;
               }
               else if (charVal == 'M') {
                  if (digits.length() == 0) {
                     return null;
                  }
                  month = Integer.parseInt(digits);
                  moveAheadIndication = 7;
                } else {
                    return null;
                }
                break;
            case 7:
               return null;
            default:
               return null;
            }
        }

        return new XSYearMonthDuration(year, month, negative);
    }

    /**
     * A method to construct an xdm sequence comprising a
     * xs:yearMonthDuration value, given input data as argument
     * to this method.
     */
	public ResultSequence constructor(ResultSequence arg) {
        ResultSequence resultSeq = new ResultSequence();
        
        if (arg.size() == 0) {
           return resultSeq;     
        }
        
        XSAnyType xsAnyType = (XSAnyType)arg.item(0);
        
        XSDuration xsDuration = castToYearMonthDuration(xsAnyType);
        
        resultSeq.add(xsDuration);

        return resultSeq;	
	}

	/**
	 * Get the data type's name.
	 * 
	 * @return   the string value 'yearMonthDuration', which is this
	 *           data type's name.
	 */
	public String typeName() {
		return "yearMonthDuration";
	}
	
	/**
     * Check whether, this duration value represents a backward passage
     * through time.
     * 
     * @return 'true' if this duration value represents a backward passage
     *         through time. 'false' otherwise.
     */
    public boolean negative() {
        return _negative;
    }
	
	/**
     * Get a string representation, of the duration value represented
     * by this object.
     * 
     * @return   String representation, of the duration value represented
     *           by this object. 
     */
    public String stringValue() {
        String strVal = "";

        if (negative()) {
           strVal += "-";
        }

        strVal += "P";

        int years = year();
        if (years != 0) {
           strVal += years + "Y";
        }

        int months = month();
        if (months == 0) {
           if (years == 0) {
              strVal += months + "M";
           }
        } else {
           strVal += months + "M";
        }

        return strVal;
    }

	/**
	 * Get the data type's name.
	 * 
	 * @return  the string value 'xs:yearMonthDuration'
	 */
	public String stringType() {
	   return XS_YEAR_MONTH_DURATION;
	}
	
	/**
     * Get the duration of time as the number of months, equivalent
     * to this duration object.
     * 
     * @return   number of months, equivalent to the duration of time
     *           represented by this object.
     */
    public int monthValue() {
       int retVal = (year() * 12) + month();

       if (negative())
          retVal *= -1;

       return retVal;
    }
    
    /**
     * Add two XSYearMonthDuration values, and return the result
     * as an XSYearMonthDuration value.
     */
    public XSYearMonthDuration add(XSYearMonthDuration arg) {
       XSYearMonthDuration result = new XSYearMonthDuration(monthValue() + 
                                                                arg.monthValue());       
       return result; 
    }
    
    /**
     * Subtract one XSYearMonthDuration value from another XSYearMonthDuration value,
     * and return the result as an XSYearMonthDuration value.
     */
    public XSYearMonthDuration subtract(XSYearMonthDuration arg) {
       XSYearMonthDuration result = new XSYearMonthDuration(monthValue() - 
                                                                arg.monthValue());       
       return result; 
    }
    
    /**
     * Do a data type cast, of a XSAnyType value to an XSDuration
     * value. 
     */
    private XSDuration castToYearMonthDuration(XSAnyType xsAnyType) {
        if (xsAnyType instanceof XSDuration) {
           XSDuration xsDuration = (XSDuration) xsAnyType;
           
           return new XSYearMonthDuration(xsDuration.year(), xsDuration.month(), 
                                          xsDuration.negative());
        }
        
        return parseYearMonthDuration(xsAnyType.stringValue());
    }
	
}
