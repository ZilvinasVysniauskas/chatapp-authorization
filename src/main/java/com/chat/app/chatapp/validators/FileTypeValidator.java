package com.chat.app.chatapp.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class FileTypeValidator implements ConstraintValidator<ValidFileType, String> {

    private static final List<String> VALID_FILE_TYPES = List.of("application/pdf",
            "image/png", "image/jpeg", "image/jpg", "image/gif", "image/tiff", "image/bmp",
            "image/webp", "video/mp4", "video/mpeg", "video/ogg", "video/quicktime", "video/webm",
            "video/x-msvideo", "video/x-flv", "video/x-ms-wmv", "video/3gpp", "video/3gpp2");

    @Override
    public boolean isValid(String fileType, ConstraintValidatorContext constraintValidatorContext) {
        return VALID_FILE_TYPES.contains(fileType.toLowerCase());
    }
}
