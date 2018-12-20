package com.kspt.buro.controller;

import com.kspt.buro.domain.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ControllerUtils {

    static Map<String, String> getErrors(BindingResult bindingResult) {
        Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                FieldError::getDefaultMessage
        );
        return bindingResult.getFieldErrors().stream().collect(collector);
    }

    static void saveFile(String uploadPath, Request request, MultipartFile file) throws IOException {

        if (file != null
                && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) uploadDir.mkdir();

            String uuidFile = UUID.randomUUID().toString().substring(0, 5);
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));
            request.setFilename(resultFilename);
        }
    }

//    static Iterable<Request> refreshRequests(Iterable<Request> requests) {
//        long i = 1;
//        for (Request request : requests) {
//            request.setId(i);
//            i++;
//        }
//        return requests;
//    }
}
