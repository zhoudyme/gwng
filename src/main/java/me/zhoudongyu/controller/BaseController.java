package me.zhoudongyu.controller;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
    protected String getStringValue(HttpServletRequest req, String s) {
        try {
            return StringUtils.isNotBlank(req.getParameter(s)) ? req.getParameter(s) : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    protected <M extends Enum> M getEnumValue(HttpServletRequest req, Class<M> clazz, String s) {
        try {
            return StringUtils.isNotBlank(req.getParameter(s)) ? (M) Enum.valueOf(clazz, req.getParameter(s)) : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected Integer getIntegerValue(HttpServletRequest req, String s, Integer defaultValue) {
        try {
            return Integer.valueOf(req.getParameter(s));
        } catch (Exception e) {
            e.printStackTrace();

            return null == defaultValue ? 1 : defaultValue;
        }
    }
}
