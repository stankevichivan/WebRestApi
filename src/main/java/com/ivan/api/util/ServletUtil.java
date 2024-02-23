package com.ivan.api.util;

import lombok.experimental.UtilityClass;

import javax.servlet.http.HttpServletRequest;

@UtilityClass
public class ServletUtil {
    public static long getIdParam(HttpServletRequest request) {
        var paramId = request.getParameter("id");
        long id = paramId == null ? 0 : Long.parseLong(paramId);
        return id;
    }
}
