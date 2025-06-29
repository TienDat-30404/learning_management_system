package com.example.enrollment_service.context;

import java.util.HashMap;
import java.util.Map;

public class MessageContextHolder {
    private static final ThreadLocal<Map<String, String>> contextHolder = new ThreadLocal<>();

    public static void setHeaders(Map<String, String> headers) {
        contextHolder.set(headers);
    }

    public static String getHeader(String headerName) {
        Map<String, String> headers = contextHolder.get();
        return headers != null ? headers.get(headerName) : null;
    }

    public static void clear() {
        contextHolder.remove();
    }
}
