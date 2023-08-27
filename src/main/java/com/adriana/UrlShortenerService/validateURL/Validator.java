package com.adriana.UrlShortenerService.validateURL;

import org.apache.commons.validator.routines.UrlValidator;

public class Validator {
    public boolean isValidUrl(String url){
        String[] schemes = {"http", "https"};
        UrlValidator urlValidator = new UrlValidator(schemes);
        return urlValidator.isValid(url);
    }
}
