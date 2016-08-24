package com.cusbee.yoki.utils;

public class ErrorCodes {
    public interface Common {
        int EMPTY_REQUEST = 1000;
        int EMPTY_REQUEST_ID = 1001;
        int NOT_EXIST = 1002;
        int INVALID_REQUEST = 1003;
        int KEY_CANNOT_BE_EMPTY = 1004;
        int NO_MAPPING_EXISTS = 1005;
        int INVALID_DATE_FORMAT = 1006;
    }

    public interface User {
        int INVALID_AUTHORITY = 2000;
        int EMPTY_FIELDS = 2001;
        int ALREADY_EXIST = 2002;
        int BAD_REQUEST = 2003;
        int USER_UNAVAILABLE = 2004;
        int INVALID_EMAIL = 2005;
        int IVALID_FIRST_OR_LAST_NAME = 2006;
        int INVALID_USERNAME = 2007;
        int INVALID_PASSWORD = 2008;
        int WRONG_OLD_PASSWORD = 2009;
    }

    public interface Category {
        int EMPTY_FIELD = 3001;
        int ALREADY_EXIST = 3002;
        int INVALID_REQUEST = 3003;
    }

    public interface Dish {
        int INVALID_REQUEST = 5003;
    }

    public interface Order {
        int NO_CLIENT_ASSIGNED = 6001;
        int EMPTY_LIST_OF_DISHES = 6002;
        int COULD_NOT_WRITEOFF_DISH = 6003;
        int WRITEOFF_ERROR = 6004;
        int NO_CRITERIA_FOR_HISTORY = 6005;
        int EMPTY_DECLINE_MESSAGE = 6006;
        int INVALID_STATUS = 6007;
    }

    public interface Client {
        int EMPTY_PHONE_NUMBER = 7001;
        int INVALID_PHONE_NUMBER = 7002;
    }

    public interface Image {
        int INVALID_REQUEST = 8000;
    }
}
