package com.openelements.opendata.base;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public class HttpUtils {

    @NonNull
    public static Language getLanguageFromHeader(@NonNull final HttpServletRequest request) {
        final String language = request.getLocale()
                .getLanguage();
        if (Objects.equals(language, "de")) {
            return Language.DE;
        } else if (Objects.equals(language, "en")) {
            return Language.EN;
        }
        return Language.EN;
    }

    public static String getLanguageForHeader(@NonNull final Language language) {
        if (language == Language.DE) {
            return "de";
        } else if (language == Language.EN) {
            return "en";
        }
        return "en";
    }

    public static <T> ResponseEntity<T> buildResponse(final T value, @NonNull final Language language) {
        final String contentLanguage = HttpUtils.getLanguageForHeader(language);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_LANGUAGE, contentLanguage)
                .body(value);
    }

}
