package com.balh.perucate.agreggates.constants;

public class Constants {
    //Codes
    public static final Integer CODE_SUCCESS=200;
    public static final Integer CODE_ERROR=400;

    //MESSAGES
    public static final String MESS_SUCCESS="Execution successful";
    public static final String MESS_ERROR="Execution error";
    public static final String MESS_ERROR_DATA_NOT_VALID="Error: Invalid data, ID or field not valid";
    public static final String MESS_NON_DATA="Error: Non data or record exists";
    public static final String MESS_NOT_FOUND_ID="Error: Id not exists or is not correct";
    public static final String MESS_ZERO_ROWS="Error: No rows or zero rows for this request";
    public static final String MESS_ERROR_NOT_DELETE = "Error: Fail deleting, something wrong happened";
    public static final String MESS_ERROR_NOT_FIND = "Error: Fail searching, something wrong happened";
    public static final String MESS_ERROR_NOT_UPDATE ="Error: Fail updating, something wrong happened";
    public static final String MESS_NON_DATA_RENIEC = "Non data in RENIEC database";

    //CodType
    public static final String COD_TYPE_DNI = "01";

    //data
    public static final Integer LENGTH_DNI=8;
    public static final Integer LENGTH_PHONE_NUMBER = 9;

    //Status
    public static final Integer STATUS_ACTIVE=1;
    public static final Integer STATUS_INACTIVE=0;

    //AUDIT
    public static final String AUDIT_ADMIN="ADMIN";
    public static final String AUDIT_USER="AUTHORIZED USER";

    //REDISKEY
    public static final String REDIS_KEY_API_RENIEC_INFO="API:RENIEC:DATAFETCH";
    public static final String REDIS_KEY_INFO_RENIEC_REDIS="API:RENIEC:DATA";
}
