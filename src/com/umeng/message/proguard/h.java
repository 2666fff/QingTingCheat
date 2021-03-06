package com.umeng.message.proguard;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import org.xmlpull.v1.XmlSerializer;

class h
  implements XmlSerializer
{
  private static final String[] a = { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "&quot;", null, null, null, "&amp;", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "&lt;", null, "&gt;", null };
  private static final int b = 8192;
  private final char[] c = new char[8192];
  private int d;
  private Writer e;
  private OutputStream f;
  private CharsetEncoder g;
  private ByteBuffer h = ByteBuffer.allocate(8192);
  private boolean i;

  private void a()
    throws IOException
  {
    int j = this.h.position();
    if (j > 0)
    {
      this.h.flip();
      this.f.write(this.h.array(), 0, j);
      this.h.clear();
    }
  }

  private void a(char paramChar)
    throws IOException
  {
    int j = this.d;
    if (j >= 8191)
    {
      flush();
      j = this.d;
    }
    this.c[j] = paramChar;
    this.d = (j + 1);
  }

  private void a(String paramString)
    throws IOException
  {
    a(paramString, 0, paramString.length());
  }

  private void a(String paramString, int paramInt1, int paramInt2)
    throws IOException
  {
    if (paramInt2 > 8192)
    {
      int k = paramInt1 + paramInt2;
      if (paramInt1 < k)
      {
        int m = paramInt1 + 8192;
        if (m < k);
        for (int n = 8192; ; n = k - paramInt1)
        {
          a(paramString, paramInt1, n);
          paramInt1 = m;
          break;
        }
      }
    }
    else
    {
      int j = this.d;
      if (j + paramInt2 > 8192)
      {
        flush();
        j = this.d;
      }
      paramString.getChars(paramInt1, paramInt1 + paramInt2, this.c, j);
      this.d = (j + paramInt2);
    }
  }

  private void a(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws IOException
  {
    if (paramInt2 > 8192)
    {
      int k = paramInt1 + paramInt2;
      if (paramInt1 < k)
      {
        int m = paramInt1 + 8192;
        if (m < k);
        for (int n = 8192; ; n = k - paramInt1)
        {
          a(paramArrayOfChar, paramInt1, n);
          paramInt1 = m;
          break;
        }
      }
    }
    else
    {
      int j = this.d;
      if (j + paramInt2 > 8192)
      {
        flush();
        j = this.d;
      }
      System.arraycopy(paramArrayOfChar, paramInt1, this.c, j, paramInt2);
      this.d = (j + paramInt2);
    }
  }

  private void b(String paramString)
    throws IOException
  {
    int j = 0;
    int k = paramString.length();
    int m = (char)a.length;
    String[] arrayOfString = a;
    int n = 0;
    if (n < k)
    {
      int i1 = paramString.charAt(n);
      if (i1 >= m);
      while (true)
      {
        n++;
        break;
        String str = arrayOfString[i1];
        if (str != null)
        {
          if (j < n)
            a(paramString, j, n - j);
          j = n + 1;
          a(str);
        }
      }
    }
    if (j < n)
      a(paramString, j, n - j);
  }

  private void b(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws IOException
  {
    int j = (char)a.length;
    String[] arrayOfString = a;
    int k = paramInt1 + paramInt2;
    int m = paramInt1;
    if (paramInt1 < k)
    {
      int n = paramArrayOfChar[paramInt1];
      if (n >= j);
      while (true)
      {
        paramInt1++;
        break;
        String str = arrayOfString[n];
        if (str != null)
        {
          if (m < paramInt1)
            a(paramArrayOfChar, m, paramInt1 - m);
          m = paramInt1 + 1;
          a(str);
        }
      }
    }
    if (m < paramInt1)
      a(paramArrayOfChar, m, paramInt1 - m);
  }

  public XmlSerializer attribute(String paramString1, String paramString2, String paramString3)
    throws IOException, IllegalArgumentException, IllegalStateException
  {
    a(' ');
    if (paramString1 != null)
    {
      a(paramString1);
      a(':');
    }
    a(paramString2);
    a("=\"");
    b(paramString3);
    a('"');
    return this;
  }

  public void cdsect(String paramString)
    throws IOException, IllegalArgumentException, IllegalStateException
  {
    throw new UnsupportedOperationException();
  }

  public void comment(String paramString)
    throws IOException, IllegalArgumentException, IllegalStateException
  {
    throw new UnsupportedOperationException();
  }

  public void docdecl(String paramString)
    throws IOException, IllegalArgumentException, IllegalStateException
  {
    throw new UnsupportedOperationException();
  }

  public void endDocument()
    throws IOException, IllegalArgumentException, IllegalStateException
  {
    flush();
  }

  public XmlSerializer endTag(String paramString1, String paramString2)
    throws IOException, IllegalArgumentException, IllegalStateException
  {
    if (this.i)
      a(" />\n");
    while (true)
    {
      this.i = false;
      return this;
      a("</");
      if (paramString1 != null)
      {
        a(paramString1);
        a(':');
      }
      a(paramString2);
      a(">\n");
    }
  }

  public void entityRef(String paramString)
    throws IOException, IllegalArgumentException, IllegalStateException
  {
    throw new UnsupportedOperationException();
  }

  public void flush()
    throws IOException
  {
    if (this.d > 0)
    {
      if (this.f == null)
        break label105;
      CharBuffer localCharBuffer = CharBuffer.wrap(this.c, 0, this.d);
      for (CoderResult localCoderResult = this.g.encode(localCharBuffer, this.h, true); ; localCoderResult = this.g.encode(localCharBuffer, this.h, true))
      {
        if (localCoderResult.isError())
          throw new IOException(localCoderResult.toString());
        if (!localCoderResult.isOverflow())
          break;
        a();
      }
      a();
      this.f.flush();
    }
    while (true)
    {
      this.d = 0;
      return;
      label105: this.e.write(this.c, 0, this.d);
      this.e.flush();
    }
  }

  public int getDepth()
  {
    throw new UnsupportedOperationException();
  }

  public boolean getFeature(String paramString)
  {
    throw new UnsupportedOperationException();
  }

  public String getName()
  {
    throw new UnsupportedOperationException();
  }

  public String getNamespace()
  {
    throw new UnsupportedOperationException();
  }

  public String getPrefix(String paramString, boolean paramBoolean)
    throws IllegalArgumentException
  {
    throw new UnsupportedOperationException();
  }

  public Object getProperty(String paramString)
  {
    throw new UnsupportedOperationException();
  }

  public void ignorableWhitespace(String paramString)
    throws IOException, IllegalArgumentException, IllegalStateException
  {
    throw new UnsupportedOperationException();
  }

  public void processingInstruction(String paramString)
    throws IOException, IllegalArgumentException, IllegalStateException
  {
    throw new UnsupportedOperationException();
  }

  public void setFeature(String paramString, boolean paramBoolean)
    throws IllegalArgumentException, IllegalStateException
  {
    if (paramString.equals("http://xmlpull.org/v1/doc/features.html#indent-output"))
      return;
    throw new UnsupportedOperationException();
  }

  public void setOutput(OutputStream paramOutputStream, String paramString)
    throws IOException, IllegalArgumentException, IllegalStateException
  {
    if (paramOutputStream == null)
      throw new IllegalArgumentException();
    try
    {
      this.g = Charset.forName(paramString).newEncoder();
      this.f = paramOutputStream;
      return;
    }
    catch (IllegalCharsetNameException localIllegalCharsetNameException)
    {
      throw ((UnsupportedEncodingException)new UnsupportedEncodingException(paramString).initCause(localIllegalCharsetNameException));
    }
    catch (UnsupportedCharsetException localUnsupportedCharsetException)
    {
      throw ((UnsupportedEncodingException)new UnsupportedEncodingException(paramString).initCause(localUnsupportedCharsetException));
    }
  }

  public void setOutput(Writer paramWriter)
    throws IOException, IllegalArgumentException, IllegalStateException
  {
    this.e = paramWriter;
  }

  public void setPrefix(String paramString1, String paramString2)
    throws IOException, IllegalArgumentException, IllegalStateException
  {
    throw new UnsupportedOperationException();
  }

  public void setProperty(String paramString, Object paramObject)
    throws IllegalArgumentException, IllegalStateException
  {
    throw new UnsupportedOperationException();
  }

  public void startDocument(String paramString, Boolean paramBoolean)
    throws IOException, IllegalArgumentException, IllegalStateException
  {
    StringBuilder localStringBuilder = new StringBuilder().append("<?xml version='1.0' encoding='utf-8' standalone='");
    if (paramBoolean.booleanValue());
    for (String str = "yes"; ; str = "no")
    {
      a(str + "' ?>\n");
      return;
    }
  }

  public XmlSerializer startTag(String paramString1, String paramString2)
    throws IOException, IllegalArgumentException, IllegalStateException
  {
    if (this.i)
      a(">\n");
    a('<');
    if (paramString1 != null)
    {
      a(paramString1);
      a(':');
    }
    a(paramString2);
    this.i = true;
    return this;
  }

  public XmlSerializer text(String paramString)
    throws IOException, IllegalArgumentException, IllegalStateException
  {
    if (this.i)
    {
      a(">");
      this.i = false;
    }
    b(paramString);
    return this;
  }

  public XmlSerializer text(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws IOException, IllegalArgumentException, IllegalStateException
  {
    if (this.i)
    {
      a(">");
      this.i = false;
    }
    b(paramArrayOfChar, paramInt1, paramInt2);
    return this;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.message.proguard.h
 * JD-Core Version:    0.6.2
 */