/*
 * ~ Copyright (c) 2021
 * ~ Dev : Amir Bahador , Amiri
 * ~ City : Iran / Abadan
 * ~ time & date : 4/4/21 4:25 PM
 * ~ email : abadan918@gmail.com
 */

package ir.atgroup.cardbox.utils.HiWeb;

import java.util.Map;

public class HiWebListener {

    public interface STATUS {
        default void onStart() {
        }

        void onResult(boolean isConnected);

        default void onError(int errorCode) {
        }

        default void onException(Exception e) {
        }
    }

    public interface GET {
        default void onStart() {
        }

        default void onEnd() {
        }

        default void onProgress(Integer values) {
        }

        void onResult(String result);

        default void onError(int errorCode) {
        }

        default void onException(Exception e) {
        }

        default Map<String, String> getHeader() {
            return null;
        }
    }

    public interface POST {
        default void onStart() {
        }

        default void onEnd() {
        }

        default void onProgress(Integer values) {
        }

        void onResult(String result);

        default void onError(int errorCode) {
        }

        default void onException(Exception e) {
        }

        default Map<String, String> getHeader() {
            return null;
        }

        default String getBody() {
            return null;
        }
    }
}
