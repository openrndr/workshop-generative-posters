import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.zip.GZIPInputStream;

/**
 * Created by edwin on 12/08/17.
 */
public class URLFetcher {
    //static Logger logger = LoggerFactory.getLogger(Scraper.class);

    static private boolean sslDisabled = false;

    private static void disableSSL() {
        // this is some nasty work copied from http://www.nakov.com/blog/2009/07/16/disable-certificate-validation-in-java-ssl-connections/
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
            };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        sslDisabled = true;
    }

    public static String fetch(URL url, int connecTimeOut, int readTimeout) throws IOException {
        if (!sslDisabled) {
            disableSSL();
        }

        HttpURLConnection con = null;
        InputStream in = null;
        InputStream gzin = null;

        try {
            con = (HttpURLConnection) url.openConnection();
            con.setReadTimeout(readTimeout);
            con.setConnectTimeout(connecTimeOut);
            con.setUseCaches(false);

            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36");
            if (con instanceof HttpsURLConnection) {
                ((HttpsURLConnection) con).setHostnameVerifier((arg0, arg1) -> {
                    return true;
                });
            }

            int responseCode = con.getResponseCode();
            String ct;
            if (responseCode == 302 || responseCode == 301 || responseCode == 303) {
                ct = con.getHeaderField("Location");
                con.disconnect();
                con = (HttpURLConnection) (new URL(ct)).openConnection();
                con.setUseCaches(false);
                con.setReadTimeout(readTimeout);
                con.setConnectTimeout(connecTimeOut);
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36");
                if (con instanceof HttpsURLConnection) {
                    ((HttpsURLConnection) con).setHostnameVerifier((arg0, arg1) -> {
                        return true;
                    });
                }
            }

            in = con.getInputStream();
            String encoding = con.getContentEncoding();
            if (encoding != null) {
                if ("gzip".equalsIgnoreCase(encoding)) {
                    gzin = new GZIPInputStream((InputStream) in);
                } else {
                    System.err.println("WARN: unsupported Content-Encoding: " + encoding);
                }
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[4096];

            InputStream finalIn = (gzin != null)? gzin : in;

            int r;

            BufferedReader br = new BufferedReader(new InputStreamReader(finalIn));

            int iter = 0;

            while ((r = (finalIn).read(buf)) != -1) {
                bos.write(buf, 0, r);
                iter++;
            }

            byte[] data = bos.toByteArray();
            bos.close();
            return bos.toString();

        } finally {
            if (in != null) {
                ((InputStream) in).close();
            }
            if (gzin != null) {
                gzin.close();
            }

            if (con != null) {
                con.disconnect();
            }
        }
    }
}


