package com.ljch;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.Uri;
import android.text.TextUtils;
import cn.com.sdk.base.common.tools.log.LogUtils;
import com.amap.api.services.district.DistrictSearchQuery;
import com.host.model.H5UrlConfigInfo;
import com.rdf.cdk.business.BConstants;
import com.rdf.cdk.interfaces.InterfaceHelper;
import com.rdf.cdk.interfaces.Interfaces.Header;
import com.rdf.cdk.jni.Common;
import com.rdf.cdk.model.BaseNetData;
import com.rdf.cdk.model.ShareUserLogin;
import com.rdf.cdk.util.CodeUtil;
import com.rdf.cdk.util.L;
import com.rdf.cdk.util.MD5;
import com.rdf.cdk.util.MemoryCache;
import com.rdf.cdk.util.NetUtils;
import com.rdf.cdk.util.SharedPreferencesTools;
import com.rdf.cdk.util.Tools;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class BaseAccessor {
    private static String[] a = new String[]{"accountAlias", "agentId", "appVersion", "appType", "accountNo", "brand", "channel", "ckStr", "cmsWhere", "deviceVersion", "deviceVer", "interfaceVersion", "iswx", "logicVID", "lskey", "manufacturer", DistrictSearchQuery.KEYWORDS_PROVINCE, "platform", "user-agent-key", "uuid", "uid", "screenRes", "sdkVer", "vest", "vId", "token", "nonce", "timestamp"};

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static ResponseResult a(HttpResponse httpResponse, AndroidHttpClient androidHttpClient, HttpRequestBase httpRequestBase) {
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        ResponseResult responseResult = new ResponseResult();
        responseResult.httpStatus = statusCode;
        if (statusCode == 200) {
            try {
                String a = a(AndroidHttpClient.getUngzippedContent(httpResponse.getEntity()));
                responseResult.responseText = a;
                if (a.length() > 0) {
                    responseResult.bizStatus = 0;
                } else {
                    responseResult.bizStatus = 2;
                }
                closeHttpResource(httpRequestBase, androidHttpClient);
            } catch (Throwable e) {
                L.e("BaseAcessor", "ResponseHandle, Data convert error!!!\n");
                L.e("BaseAcessor", e);
                responseResult.bizStatus = 2;
            } catch (Throwable th) {
                closeHttpResource(httpRequestBase, androidHttpClient);
            }
        } else {
            L.e("BaseAcessor", "ResponseHandle, HttpStatus: " + statusCode + LogUtils.LEFT_BRA + httpResponse.getStatusLine().getReasonPhrase() + LogUtils.RIGHT_BRA);
            closeHttpResource(httpRequestBase, androidHttpClient);
        }
        return responseResult;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static String a(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8192);
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    stringBuilder.append(readLine + "\n");
                } else {
                    try {
                        break;
                    } catch (IOException e) {
                    }
                }
            }
            inputStream.close();
        } catch (IOException e2) {
            stringBuilder.delete(0, stringBuilder.length());
        } catch (Throwable th) {
            try {
                inputStream.close();
            } catch (IOException e3) {
            }
        }
        return stringBuilder.toString();
    }

    private static String a(String str, boolean z) {
        if (!z && str.startsWith("https://")) {
            str = str.replaceFirst("https://", "http://");
        }
        return (Constants.CHANGE_TO_HTTP && str.startsWith("https://")) ? str.replaceFirst("https://", "http://") : str;
    }

    private static HashMap<String, String> a(Context context, String str) {
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("cms_where", BConstants.InstallChannelId);
        hashMap.put("vId", Constants.ClientVersion);
        hashMap.put("logicVID", Constants.LogicClientVersion);
        hashMap.put("vest", "ticai");
        hashMap.put("uuid", Tools.getIMEI(context));
        hashMap.put("agentId", "110001");
        hashMap.put("accountAlias", BConstants.ACCOUNTALIAS);
        hashMap.put("channel", BConstants.InstallChannelId);
        hashMap.put("interfaceVersion", "1");
        hashMap.put("ckStr", ShareUserLogin.getUserLoginInfo().getUSER_LSKEY());
        hashMap.put("deviceVersion", Tools.getOsVersion());
        hashMap.put("appVersion", Constants.ClientVersion);
        hashMap.put("user-agent-key", "4ed92dce-9c09-5554-8b3c-d4dfe43109a5");
        if (!TextUtils.isEmpty(str)) {
            hashMap.put("accountType", str);
        }
        if (ShareUserLogin.getUserLoginInfo().getUSER_LSKEY() == null || ShareUserLogin.getUserLoginInfo().getUSER_LSKEY().equals("")) {
            hashMap.put("lskey", "");
            hashMap.put("ckStr", "");
        } else {
            hashMap.put("lskey", ShareUserLogin.getUserLoginInfo().getUSER_LSKEY());
            hashMap.put("ckStr", ShareUserLogin.getUserLoginInfo().getUSER_LSKEY());
        }
        if (!(ShareUserLogin.getUserLoginInfo().getSESSION_KEY() == null || ShareUserLogin.getUserLoginInfo().getSESSION_KEY().equals(""))) {
            hashMap.put("sessionkey", ShareUserLogin.getUserLoginInfo().getSESSION_KEY());
        }
        if (!(ShareUserLogin.getUserLoginInfo().getUSER_UIN() == null || ShareUserLogin.getUserLoginInfo().getUSER_UIN().equals(""))) {
            hashMap.put("uin", ShareUserLogin.getUserLoginInfo().getUSER_UIN());
        }
        if (TextUtils.isEmpty(ShareUserLogin.getUserLoginInfo().getUSER_ID())) {
            hashMap.put("uid", "");
            hashMap.put("accountNo", "");
        } else {
            hashMap.put("uid", ShareUserLogin.getUserLoginInfo().getUSER_ID());
            hashMap.put("accountNo", ShareUserLogin.getUserLoginInfo().getUSER_ID());
        }
        if (ShareUserLogin.getUserLoginInfo().getWxUserFlag()) {
            hashMap.put("iswx", "1");
            if (!TextUtils.isEmpty(ShareUserLogin.getUserLoginInfo().getUSER_LSKEY())) {
                hashMap.put("wcp_qlskey", ShareUserLogin.getUserLoginInfo().getUSER_LSKEY());
            }
        } else {
            hashMap.put("iswx", "0");
        }
        if (ShareUserLogin.getUserLoginInfo().isPhoneType()) {
            hashMap.put("iswx", "2");
        }
        hashMap.put(DistrictSearchQuery.KEYWORDS_PROVINCE, BConstants.PROVINCE);
        hashMap.put("nonce", UUID.randomUUID().toString());
        hashMap.put("timestamp", System.currentTimeMillis());
        hashMap.put("appType", "ticai_android");
        hashMap.put("sdkVer", Tools.getOsVersion());
        hashMap.put("deviceVer", Tools.getOsDeviceVer());
        hashMap.put("manufacturer", Tools.getManufacturer());
        hashMap.put("brand", Tools.getBrand());
        hashMap.put("token", SharedPreferencesTools.getLocalData(BConstants.KEY_XG_TOKEN, context, new String[0]));
        hashMap.put("cmsWhere", BConstants.InstallChannelId);
        hashMap.put("platform", "1");
        int[] screenResolution = Tools.getScreenResolution(context);
        if (screenResolution.length > 1) {
            hashMap.put("screenRes", screenResolution[0] + BConstants.CONTENTSPLITEFLAG_HLine + screenResolution[1]);
        } else {
            hashMap.put("screenRes", "");
        }
        if (!TextUtils.isEmpty(Constants.ENV_MODE)) {
            hashMap.put("env", Constants.ENV_MODE);
        }
        return hashMap;
    }

    private static Map<String, String> a(Map<String, String> map) {
        Map<String, String> hashMap = new HashMap();
        for (int i = 0; i < a.length; i++) {
            hashMap.put(a[i], map.get(a[i]));
        }
        return hashMap;
    }

    private static HttpHost a(Context context) {
        HttpHost httpHost;
        if (context != null) {
            try {
                NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                if (activeNetworkInfo == null || !NetUtils.NET_WIFI.equalsIgnoreCase(activeNetworkInfo.getTypeName())) {
                    String defaultHost = Proxy.getDefaultHost();
                    int defaultPort = Proxy.getDefaultPort();
                    if (defaultHost != null) {
                        httpHost = new HttpHost(defaultHost, defaultPort);
                        return httpHost;
                    }
                }
            } catch (Throwable e) {
                L.e("BaseAcessor", e);
                return null;
            }
        }
        httpHost = null;
        return httpHost;
    }

    private static void a(HttpRequestBase httpRequestBase) {
        Header commHeader = InterfaceHelper.getInstance().getCommHeader();
        if (commHeader != null && commHeader.consts != null) {
            int length = commHeader.consts.length;
            for (int i = 0; i < length; i++) {
                httpRequestBase.addHeader(commHeader.consts[i].key, commHeader.consts[i].value);
            }
        }
    }

    public static boolean chechNetStateByResponse(String str, BaseNetData baseNetData) {
        if (str.equalsIgnoreCase("2")) {
            baseNetData.Net_State = 2;
            return false;
        } else if (str.equalsIgnoreCase("1")) {
            baseNetData.Net_State = 1;
            return false;
        } else {
            baseNetData.Net_State = 3;
            return true;
        }
    }

    public static void closeHttpResource(HttpRequestBase httpRequestBase, AndroidHttpClient androidHttpClient) {
        if (androidHttpClient != null) {
            androidHttpClient.close();
        }
        if (httpRequestBase != null) {
            httpRequestBase.abort();
        }
    }

    public static ResponseResult doGetRequest(Context context, String str, Map<String, String> map) {
        AndroidHttpClient newInstanceHttps;
        HttpRequestBase httpGet;
        Exception e;
        if (Tools.checkActionNet(context)) {
            boolean z;
            ResponseResult a;
            if (NetUtils.isHttps(str)) {
                try {
                    newInstanceHttps = AndroidHttpClient.newInstanceHttps(context);
                    z = true;
                } catch (HttpsException e2) {
                    L.e("BaseAcessor", "客户端不支持https协议, 切换至http协议.\n" + e2.toString());
                }
                str = a(str, z);
                httpGet = new HttpGet(str);
                try {
                    httpGet.addHeader("seckey", getSeckey(map));
                    a = a(doRequest(context, newInstanceHttps, httpGet, map, null), newInstanceHttps, httpGet);
                    ResponseResult.wrapperResponseResult(a, str, map, null);
                    return a;
                } catch (Exception e3) {
                    e = e3;
                    L.monitor("BaseAcessor", "Net Error[2]\n" + e);
                    closeHttpResource(httpGet, newInstanceHttps);
                    return ResponseResult.newExceptionResult(e, str, map, null);
                }
            }
            newInstanceHttps = AndroidHttpClient.newInstance(context);
            z = false;
            try {
                str = a(str, z);
                httpGet = new HttpGet(str);
                httpGet.addHeader("seckey", getSeckey(map));
                a = a(doRequest(context, newInstanceHttps, httpGet, map, null), newInstanceHttps, httpGet);
                ResponseResult.wrapperResponseResult(a, str, map, null);
                return a;
            } catch (Exception e4) {
                e = e4;
                httpGet = null;
                L.monitor("BaseAcessor", "Net Error[2]\n" + e);
                closeHttpResource(httpGet, newInstanceHttps);
                return ResponseResult.newExceptionResult(e, str, map, null);
            }
        }
        L.monitor("BaseAcessor", "Local Net Error[1]");
        return ResponseResult.newNoNetResult(str, map, null);
    }

    public static ResponseResult doPostRequest(Context context, String str, Map<String, String> map, Map<String, String> map2) {
        AndroidHttpClient newInstanceHttps;
        boolean z;
        List arrayList;
        HttpRequestBase httpPost;
        Exception e;
        if (NetUtils.isHttps(str)) {
            try {
                newInstanceHttps = AndroidHttpClient.newInstanceHttps(context);
                z = true;
            } catch (HttpsException e2) {
                L.e("BaseAcessor", "客户端不支持https协议, 切换至http协议.\n" + e2.toString());
            }
            arrayList = new ArrayList();
            for (Entry entry : map2.entrySet()) {
                arrayList.add(new BasicNameValuePair((String) entry.getKey(), (String) entry.getValue()));
            }
            str = a(str, z);
            httpPost = new HttpPost(str);
            try {
                httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                httpPost.setEntity(new UrlEncodedFormEntity(arrayList, "UTF-8"));
                ResponseResult a = a(doRequest(context, newInstanceHttps, httpPost, map, map2), newInstanceHttps, httpPost);
                ResponseResult.wrapperResponseResult(a, str, map, null);
                return a;
            } catch (Exception e3) {
                e = e3;
                L.e("BaseAcessor", "Net Error[2]\n" + e);
                closeHttpResource(httpPost, newInstanceHttps);
                return ResponseResult.newExceptionResult(e, str, map, map2);
            }
        }
        newInstanceHttps = AndroidHttpClient.newInstance(context);
        z = false;
        arrayList = new ArrayList();
        for (Entry entry2 : map2.entrySet()) {
            arrayList.add(new BasicNameValuePair((String) entry2.getKey(), (String) entry2.getValue()));
        }
        try {
            str = a(str, z);
            httpPost = new HttpPost(str);
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            httpPost.setEntity(new UrlEncodedFormEntity(arrayList, "UTF-8"));
            ResponseResult a2 = a(doRequest(context, newInstanceHttps, httpPost, map, map2), newInstanceHttps, httpPost);
            ResponseResult.wrapperResponseResult(a2, str, map, null);
            return a2;
        } catch (Exception e4) {
            e = e4;
            httpPost = null;
            L.e("BaseAcessor", "Net Error[2]\n" + e);
            closeHttpResource(httpPost, newInstanceHttps);
            return ResponseResult.newExceptionResult(e, str, map, map2);
        }
    }

    public static ResponseResult doPostRequest_BAK(Context context, String str, Map<String, String> map, Map<String, String> map2) {
        if (Tools.checkActionNet(context)) {
            return doPostRequest(context, str, map, map2);
        }
        L.monitor("BaseAcessor", "Local Net Error[1]");
        return ResponseResult.newNoNetResult(str, map, map2);
    }

    public static HttpResponse doRequest(Context context, AndroidHttpClient androidHttpClient, HttpRequestBase httpRequestBase, Map<String, String> map, Map<String, String> map2) {
        Object secSrcStr = getSecSrcStr(map);
        Object secSrcStr2 = getSecSrcStr(map2);
        L.i("BaseAcessor", "getParamSrc-->" + secSrcStr);
        L.i("BaseAcessor", "postParamSrc-->" + secSrcStr2);
        setClientDefaultProxy(context, androidHttpClient);
        Map genRequestParams = genRequestParams(context);
        httpRequestBase.addHeader("referer", "https://accpbet.sporttery.cn/igw/v1.0/");
        a(httpRequestBase);
        AndroidHttpClient.modifyRequestToAcceptGzipResponse(httpRequestBase);
        org.apache.http.Header[] allHeaders = httpRequestBase.getAllHeaders();
        Map hashMap = new HashMap();
        for (org.apache.http.Header header : allHeaders) {
            hashMap.put(header.getName(), header.getValue());
        }
        getSeckey(a(genRequestParams));
        Object secSrcStr3 = getSecSrcStr(a(genRequestParams));
        String str = "";
        if (TextUtils.isEmpty(secSrcStr) && TextUtils.isEmpty(secSrcStr2)) {
            String str2 = str;
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            if (!TextUtils.isEmpty(secSrcStr3)) {
                stringBuffer.append(secSrcStr3);
            }
            if (!TextUtils.isEmpty(secSrcStr)) {
                stringBuffer.append("&");
                stringBuffer.append(secSrcStr);
            }
            if (!TextUtils.isEmpty(secSrcStr2)) {
                stringBuffer.append("&");
                stringBuffer.append(secSrcStr2);
            }
            secSrcStr2 = getSignValue(Uri.decode(stringBuffer.toString()).toLowerCase()).toLowerCase();
        }
        if (map2 != null && map2.entrySet().size() > 0) {
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            JSONObject jSONObject3 = new JSONObject();
            try {
                for (Entry entry : a(genRequestParams).entrySet()) {
                    jSONObject2.put((String) entry.getKey(), entry.getValue());
                }
                jSONObject2.put("sign", secSrcStr2);
                jSONObject.put("headers", jSONObject2);
                for (Entry entry2 : map2.entrySet()) {
                    if (entry2.getValue() != null) {
                        jSONObject3.put((String) entry2.getKey(), entry2.getValue());
                    } else {
                        jSONObject3.put((String) entry2.getKey(), "");
                    }
                }
                jSONObject.put("body", jSONObject3);
                str = CodeUtil.encode(jSONObject.toString());
                List arrayList = new ArrayList();
                arrayList.add(new BasicNameValuePair(H5UrlConfigInfo.CONTENT, str));
                ((HttpPost) httpRequestBase).setEntity(new UrlEncodedFormEntity(arrayList, "UTF-8"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        HttpResponse execute = androidHttpClient.execute(httpRequestBase);
        if (execute.getStatusLine().getStatusCode() != 200) {
            return execute;
        }
        org.apache.http.Header firstHeader = execute.getFirstHeader("Content-Type");
        secSrcStr = (firstHeader == null || firstHeader.getValue() == null || firstHeader.getValue().indexOf("wap.wml") <= 0) ? null : 1;
        return secSrcStr != null ? androidHttpClient.execute(httpRequestBase) : execute;
    }

    public static HashMap<String, String> genRequestParams(Context context) {
        return a(context, "");
    }

    public static HashMap<String, String> genRequestParams(Context context, String str) {
        return a(context, str);
    }

    public static synchronized String getSecSrcStr(Map<String, String> map) {
        String str;
        synchronized (BaseAccessor.class) {
            StringBuffer stringBuffer = new StringBuffer();
            if (map == null || map.size() <= 0) {
                str = "";
            } else {
                int i;
                Object[] toArray = map.keySet().toArray();
                Arrays.sort(toArray);
                int i2 = 0;
                for (Object obj : toArray) {
                    i2 += obj.toString().length();
                }
                for (i = 0; i < toArray.length; i++) {
                    if (i == toArray.length - 1) {
                        stringBuffer.append(toArray[i].toString() + "=");
                        if (!TextUtils.isEmpty((CharSequence) map.get(toArray[i]))) {
                            stringBuffer.append(Uri.decode((String) map.get(toArray[i])));
                        }
                    } else {
                        stringBuffer.append(toArray[i].toString() + "=");
                        if (!TextUtils.isEmpty((CharSequence) map.get(toArray[i]))) {
                            stringBuffer.append(Uri.decode((String) map.get(toArray[i])));
                        }
                        stringBuffer.append(String.valueOf(i2));
                    }
                }
                str = stringBuffer.toString();
            }
        }
        return str;
    }

    public static synchronized String getSeckey(Map<String, String> map) {
        String str;
        synchronized (BaseAccessor.class) {
            StringBuffer stringBuffer = new StringBuffer();
            if (map == null || map.size() <= 0) {
                str = "";
            } else {
                Object[] toArray = map.keySet().toArray();
                Arrays.sort(toArray);
                for (int i = 0; i < toArray.length; i++) {
                    stringBuffer.append(toArray[i].toString());
                    if (!TextUtils.isEmpty((CharSequence) map.get(toArray[i]))) {
                        stringBuffer.append(Uri.decode((String) map.get(toArray[i])));
                    }
                }
                Common.encryptN(stringBuffer.toString(), "");
                MemoryCache instance = MemoryCache.getInstance();
                String crypt = MD5.crypt(stringBuffer.toString());
                str = (String) instance.getValue(crypt);
                instance.removeValue(crypt);
            }
        }
        return str;
    }

    public static synchronized String getSignValue(String str) {
        String sha256;
        synchronized (BaseAccessor.class) {
            sha256 = CodeUtil.sha256(str);
        }
        return sha256;
    }

    public static void setClientDefaultProxy(Context context, AndroidHttpClient androidHttpClient) {
        HttpHost a = a(context);
        if (a != null) {
            androidHttpClient.getParams().setParameter("http.route.default-proxy", a);
        }
    }
}
