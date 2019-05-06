package org.trahim.project.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;

@Service
public class MapValidationErrorService {

    public ResponseEntity<?> mapValidationErrorService (BindingResult result) {
        HashMap<String, String> errorsMap;

        if (result.hasErrors()) {

            int size = (int) Math.round(result.getErrorCount()/0.75)+1;
            errorsMap = new HashMap<>(size);

            for (FieldError error:
                    (result.getFieldErrors())) {
                errorsMap.put(error.getField(), error.getDefaultMessage());

            }

            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }

        return null;

    }
}
