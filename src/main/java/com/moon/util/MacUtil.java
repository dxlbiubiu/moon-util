package com.moon.util;

import com.moon.lang.ThrowUtil;
import com.moon.net.InternetUtil;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.function.Supplier;

/**
 * @author benshaoye
 */
public final class MacUtil {

    private MacUtil() {
        ThrowUtil.noInstanceError();
    }

    public final static String getLocalMacAddress() {
        return LocalMac.MAC.get();
    }

    public final static String getDecimalMacAddress() {
        return LocalMac.DECIMAL.get();
    }


    public enum LocalMac implements Supplier<String> {
        /**
         * 十六进制
         */
        MAC,
        /**
         * 十进制
         */
        DECIMAL {
            @Override
            public String get() {
                return getAddress(true);
            }
        };

        private final InetAddress address = InternetUtil.getLocalhost();

        @Override
        public String get() {
            return getAddress(false);
        }

        byte[] getMac() {
            try {
                return NetworkInterface.getByInetAddress(address).getHardwareAddress();
            } catch (SocketException e) {
                throw new IllegalStateException();
            }
        }


        String getAddress(boolean decimal) {
            final byte[] mac = getMac();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                int temp = mac[i] & 0xff;
                String str;
                if (decimal) {
                    str = Integer.toString(temp);
                } else {
                    if (i > 0) {
                        sb.append('-');
                    }
                    str = Integer.toHexString(temp);
                }

                if (str.length() == 1) {
                    sb.append("0");
                }
                sb.append(str);
            }
            return sb.toString().toUpperCase();
        }
    }
}
