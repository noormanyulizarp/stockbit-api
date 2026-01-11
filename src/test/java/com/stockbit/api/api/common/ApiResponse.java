package com.stockbit.api.api.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse<T> {

    @JsonProperty("data")
    private List<T> data;

    @JsonProperty("status")
    private String status;

    @JsonProperty("total")
    private Integer total;

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("locale")
    private String locale;

    @JsonProperty("seed")
    private String seed;

    // HTTP Status Code (not from JSON response, but from RestAssured)
    private Integer httpStatusCode;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public boolean isSuccess() {
        boolean isStatusSuccess = "OK".equalsIgnoreCase(status) || "success".equalsIgnoreCase(status);
        boolean isHttpSuccess = httpStatusCode != null && httpStatusCode >= 200 && httpStatusCode < 300;
        return isStatusSuccess && isHttpSuccess;
    }
}
