package dendy.util.http;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.net.*;
import java.net.Proxy.Type;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

public class HttpRequestUtil {
    private static final Logger LOG = LoggerFactory.getLogger(HttpRequestUtil.class);

    /**
     * @param urlStr   访问地址
     * @param params   参数信息 以"&" 隔开每个参数的值 无参数则设置为null
     * @param isGet    是否是Get 的请求方式
     * @param ctMap    contentType 的map集合 没有则设置为null
     * @param ck       cookie提交的参数信息 没有则设置为null
     * @param encoding 返回内容的编码
     * @param _proxy   代理设置，如果不使用代理则设置为null
     * @param filePath 文件路径 需要上传的文件路径，该文件路径为绝对路径，不需要上传文件时设置为null
     * @param boundary 文件流 分割符，用于识别文件的起始位置和结束位置  模拟 form-data 进行文件上传
     * @return 返回获取的URL返回信息
     */
    public static String executeRquest(String urlStr, String params,
                                       boolean isGet, Map<String, String> ctMap, Map<String, String> ck,
                                       String encoding, HttpProxy _proxy, String filePath, String boundary) {
        System.setProperty("jsse.enableSNIExtension", "false");
        System.out.println("设置的系统属性：" + System.getProperties().getProperty("jsse.enableSNIExtension"));
        String retStr = "";
        try {
            urlStr += isGet ? (params != null ? ("?" + params) : "") : "";
            URL url = new URL(urlStr);

            Proxy proxy = null;
            if (_proxy != null) {
                InetAddress id = InetAddress.getByName(_proxy.getHost_url());
                InetSocketAddress socket = new InetSocketAddress(id,
                        Integer.parseInt(_proxy.getPort()));
                proxy = new Proxy(Type.HTTP, socket);
            }

            HttpURLConnection conn = null;
            if (proxy != null) {
                conn = (HttpURLConnection) url.openConnection(proxy);
            } else {
                conn = (HttpURLConnection) url.openConnection();
            }
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setRequestMethod(isGet ? "GET" : "POST");
            /*contentType*/
            if (ctMap != null) {
                for (String key : ctMap.keySet()) {
                    if (ctMap.get(key) != null) {
                        conn.setRequestProperty(key, ctMap.get(key));
                    }
                }
            }

			/*Cookie*/
            if (ck != null) {
                String tmp = "";
                for (String key : ck.keySet()) {
                    if ("".equals(tmp)) {
                        tmp = key + "=" + ck.get(key);
                    } else {
                        tmp = ";" + key + "=" + ck.get(key);
                    }
                }
                if (tmp != null && !"".equals(tmp)) {
                    conn.setRequestProperty("Cookie", tmp);
                }
            }

            conn.connect();
            //上传文件
            File file = StringUtils.isNotBlank(filePath) ? new File(filePath) : null;

            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.write((isGet ? "" : StringUtils.trimToEmpty(params)).getBytes());
            out.flush();

            if (file != null) {
                if (file.exists()) {
                    DataInputStream in = new DataInputStream(new FileInputStream(file));
                    String contentType = new MimetypesFileTypeMap().getContentType(file);
                    System.out.println("contentType:" + contentType);
                    StringBuilder form_header = new StringBuilder();
                    form_header.append("--" + boundary + "\r\n");
                    form_header.append("Content-Disposition: form-data; name=\"media\"; filename=\"" + file.getName() + "\"; filelength=\"" + in.available() + "\"");
                    form_header.append("\r\n");
                    form_header.append("Content-Type: " + contentType);
                    form_header.append("\r\n\r\n");
                    out.write(form_header.toString().getBytes());
                    out.flush();

                    byte[] bytes = new byte[1024];
                    int len = 0;
                    while ((len = in.read(bytes)) != -1) {
                        out.write(bytes, 0, len);
                        out.flush();
                        len = 0;
                    }

                    out.write(("\r\n--" + boundary + "--\r\n").getBytes());
                    out.flush();

                    in.close();
                    out.flush();
                    out.close();
                } else {
                    throw new FileNotFoundException("file：\"" + filePath + "\" is not exist!");
                }
            }
            out.flush();
            out.close();

            if (conn.getResponseCode() == 200) {
                String content_encoding = conn.getHeaderField("Content-Encoding");// 相应的内容的类型
                BufferedReader reader = null;
                if (null != content_encoding
                        && Pattern.compile("^.{0,}(gzip){1}.{0,}", Pattern.UNICODE_CASE).matcher(content_encoding).matches()) {
                    reader = new BufferedReader(
                            new InputStreamReader(
                                    new GZIPInputStream(conn.getInputStream()), encoding));
                } else {
                    reader = new BufferedReader(
                            new InputStreamReader(conn.getInputStream(), encoding));
                }
                StringBuilder content = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
                reader.close();
                retStr = content != null ? content.toString() : "";
            } else if (conn.getResponseCode() == 401) {
                LOG.error("访问授权失败");
            } else {
                LOG.error("未知返回状态！");
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retStr;
    }
}
