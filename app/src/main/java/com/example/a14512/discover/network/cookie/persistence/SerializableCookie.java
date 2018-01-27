package com.example.a14512.discover.network.cookie.persistence;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import okhttp3.Cookie;

/**
 * Created by 14512 on 2017/8/16.
 */

public class SerializableCookie implements Serializable {

    private static final String TAG = SerializableCookie.class.getSimpleName();

    private static final long serialVersionUID = -4379853716078535580L;
    //不需要序列化
    private transient Cookie cookie;

    public String encode(Cookie cookie) {
        this.cookie = cookie;

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;

        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            Log.d(TAG, "IOException in encodeCookie", e);
            return null;
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    Log.d(TAG, "Stram not closed in encodeCookie", e);
                }
            }
        }
        return byteArrayToHexString(byteArrayOutputStream.toByteArray());
    }

    private static String byteArrayToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder(bytes.length * 2);
        for (byte element : bytes) {
            int v = element & 0xff;
            if (v < 16) {
                stringBuilder.append('0');
            }
            stringBuilder.append(Integer.toHexString(v));
        }
        return stringBuilder.toString();
    }

    public Cookie decode(String encodeCookie) {
        byte[] bytes = hexStringToByteArray(encodeCookie);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Cookie cookie = null;
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            cookie = ((SerializableCookie) objectInputStream.readObject()).cookie;
        } catch (IOException e) {
            Log.d(TAG, "IOException in decodeCookie", e);
        } catch (ClassNotFoundException e) {
            Log.d(TAG, "ClassNotFoundException in decodeCookie", e);
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    Log.d(TAG, "Stream not closed in decodeCookie", e);
                }
            }
        }
        return cookie;

    }

    /**
     * Converts hex values from strings to byte array
     *
     * @param hexString string of hex-encoded values
     * @return decoded byte array
     */
    private static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }

    private static long NON_VALID_EXPIRES_AT = -1L;

    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.writeObject(cookie.name());
        outputStream.writeObject(cookie.value());
        outputStream.writeObject(cookie.persistent() ? cookie.expiresAt() : NON_VALID_EXPIRES_AT);
        outputStream.writeObject(cookie.domain());
        outputStream.writeObject(cookie.path());
        outputStream.writeObject(cookie.secure());
        outputStream.writeObject(cookie.httpOnly());
        outputStream.writeObject(cookie.hostOnly());
    }

    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        Cookie.Builder builder = new Cookie.Builder();
        builder.name((String) inputStream.readObject());
        builder.value((String) inputStream.readObject());

        long expiresAt = inputStream.readLong();
        if (expiresAt != NON_VALID_EXPIRES_AT) {
            builder.expiresAt(expiresAt);
        }

        final String domain = (String) inputStream.readObject();
        builder.domain(domain);

        builder.path((String) inputStream.readObject());

        if (inputStream.readBoolean()) {
            builder.secure();
        }
        if (inputStream.readBoolean()) {
            builder.httpOnly();
        }
        if (inputStream.readBoolean()) {
            builder.hostOnlyDomain(domain);
        }

        cookie = builder.build();
    }
}
