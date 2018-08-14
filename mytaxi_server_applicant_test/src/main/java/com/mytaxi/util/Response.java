package com.mytaxi.util;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Response<T>
{

    public static class JsonKeys
    {

        private JsonKeys()
        {
            throw new IllegalStateException("This constructor should never be called!");
        }

        public static final String UUID = "uuid";
        public static final String DATA = "data";
        public static final String ERRORS = "errors";
    }


    public Response()
    {}


    public Response(final String uuid)
    {
        this.uuid = uuid;
    }

    @JsonProperty(JsonKeys.UUID)
    private String uuid;
    @JsonProperty(JsonKeys.DATA)
    private T data;
    @JsonProperty(JsonKeys.ERRORS)
    private List<String> errors;


    public String getUuid()
    {
        return uuid;
    }


    public void setUuid(final String uuid)
    {
        this.uuid = uuid;
    }


    public T getData()
    {
        return data;
    }


    public void setData(final T data)
    {
        this.data = data;
    }


    public List<String> getErrors()
    {
        return errors;
    }


    public void setErrors(final List<String> errors)
    {
        this.errors = errors;
    }
}
