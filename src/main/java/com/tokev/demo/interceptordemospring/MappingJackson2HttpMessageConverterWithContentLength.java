package com.tokev.demo.interceptordemospring;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;

public class MappingJackson2HttpMessageConverterWithContentLength extends MappingJackson2HttpMessageConverter {
  @Override
  protected void writeInternal(Object object, @Nullable Type type, HttpOutputMessage outputMessage)
      throws IOException, HttpMessageNotWritableException {

    if( outputMessage.getHeaders().getContentLength() < 0 ) {
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

      HttpOutputMessage newOutputMessage = new HttpOutputMessage() {
        @Override
        public OutputStream getBody() {
          return byteArrayOutputStream;
        }

        @Override
        public HttpHeaders getHeaders() {
          return outputMessage.getHeaders();
        }
      };
      super.writeInternal(object, type, newOutputMessage);

      HttpHeaders headers = outputMessage.getHeaders();
      headers.setContentLength(byteArrayOutputStream.toByteArray().length);
      StreamUtils.copy(byteArrayOutputStream.toByteArray(), outputMessage.getBody());
    } else {
      super.writeInternal(object, type, outputMessage);
    }
  }
}
