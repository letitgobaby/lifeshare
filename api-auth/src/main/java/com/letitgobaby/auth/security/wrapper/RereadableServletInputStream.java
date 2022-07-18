package com.letitgobaby.auth.security.wrapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

public class RereadableServletInputStream extends ServletInputStream {
  
  private InputStream inputStream;

  public RereadableServletInputStream(byte[] cachedBody) {
    this.inputStream = new ByteArrayInputStream(cachedBody);
  }

  @Override
  public boolean isFinished() {
    try {
      return inputStream.available() == 0;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public boolean isReady() {
    return true;
  }

  @Override
  public void setReadListener(ReadListener readListener) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int read() throws IOException {
    return inputStream.read();
  }

}
