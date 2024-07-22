package com.vietcuong.simpleSecurity.common;

public class ApplicationError {

    public static enum GlobalError {
        USER_REGISTRATION_SUCCESS("00", "USER_REGISTRATION_SUCCESS"),
        USER_REGISTRATION_ERROR("01", "USER_REGISTRATION_ERROR");


        private final String statusCode;
        private final String description;

        GlobalError(String statusCode, String description) {
            this.statusCode = statusCode;
            this.description = description;
        }

        public String getStatusCode() {
            return this.statusCode;
        }

        public String getDescription() {
            return this.description;
        }
    }
}
