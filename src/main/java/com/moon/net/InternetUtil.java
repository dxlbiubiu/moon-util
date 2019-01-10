package com.moon.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static com.moon.lang.ThrowUtil.noInstanceError;
import static com.moon.lang.ThrowUtil.throwRuntime;

/**
 * @author benshaoye
 * @date 2018/9/17
 */
public final class InternetUtil {

    private InternetUtil() {
        noInstanceError();
    }

    public final static InetAddress getLocalhost() {
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            return throwRuntime(e);
        }
    }

    public static String getLocalIP4() {
        return getLocalhost().getHostAddress();
    }
}
