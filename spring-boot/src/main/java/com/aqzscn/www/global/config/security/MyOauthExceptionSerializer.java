package com.aqzscn.www.global.config.security;

import com.aqzscn.www.global.domain.co.MyOauthException;
import com.aqzscn.www.global.domain.dto.IErrorCode;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 序列化MyOauthException中的返回信息
 *
 * @author Godbobo
 * @date 2019/5/28
 */
public class MyOauthExceptionSerializer extends StdSerializer<MyOauthException> {

    protected MyOauthExceptionSerializer() {
        super(MyOauthException.class);
    }

    @Override
    public void serialize(MyOauthException e, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        List<IErrorCode> errorCodes = new ArrayList<>();
        errorCodes.add(e.getError());
        jsonGenerator.writeObjectField("msg", e.getError().getTitle());
        jsonGenerator.writeObjectField("errors", errorCodes);
        jsonGenerator.writeEndObject();
    }
}
