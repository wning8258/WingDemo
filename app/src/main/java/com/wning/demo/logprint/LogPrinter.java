/*
 * Copyright (C) 2014 Inaka.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author Henrique Boregio (henrique@inakanetworks.com)
 */
package com.wning.demo.logprint;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;

import com.guagua.modules.utils.LogUtils;

import static android.R.id.message;

public class LogPrinter {

    public static final String ARG_OPTIONS = "logprinter.options";
    public static final String MESSAGE = "logprinter.message";
    public static final String FLAG = "logprinter.flag";
    private static final String TAG = "LogPrinter";
    private static Options sOptions;
    private static Context mContext;
    private static boolean isServiceRunning;

    public static final int WHAT_LOG = 0;
    public static final int WHAT_CLEAR = 1;
    public static final int FLAG_CLEAR = 100;

    private static final Handler UI_HANDLER = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (!isServiceRunning) {//service 停止了就不要往service发消息了
                return;
            }

            switch (msg.what){
                case WHAT_LOG:
                    String message = (String) msg.obj;
                    Intent intent = new Intent(mContext, LogPrinterService.class);
                    intent.putExtra(MESSAGE, message);
                    mContext.startService(intent);
                    break;
                case WHAT_CLEAR:
                     intent = new Intent(mContext, LogPrinterService.class);
                    intent.putExtra(FLAG, FLAG_CLEAR);
                    mContext.startService(intent);
                    break;
                default:
                    break;
            }


        }
    };


    /**
     * * Starts a new LogPrinter with custom {@link Options}
     *
     * @param context Context
     * @param options Custom {@link Options}
     */
    public static void enable(Context context, Options options) {
        // checkPermission(context);
        sOptions = options;
        mContext = context;
        init();
    }

    /**
     * Starts a new LogPrinter with default {@link Options}
     *
     * @param context Context
     */
    public static void enable(Context context) {
        enable(context, new Options.Builder().build());
    }

    private static void init() {

        Intent intent = new Intent(mContext, LogPrinterService.class);
        intent.putExtra(ARG_OPTIONS, sOptions);
        mContext.startService(intent);
        isServiceRunning = true;
    }

    public static void disable() {
        Intent intent = new Intent(mContext, LogPrinterService.class);
        mContext.stopService(intent);
        mContext = null;
        isServiceRunning = false;
    }


    /**
     * Logs a String message to the screen. This String will be overlayed on top of the
     * UI elements currently displayed on screen. As a side effect, this message will
     * also be logged to the standard output via
     *
     * @param message String to be displayed
     */
    public static void log(String message) {
        Message msg = UI_HANDLER.obtainMessage(WHAT_LOG, message);
        msg.sendToTarget();
    }

    public static void clear() {
        Message msg = UI_HANDLER.obtainMessage(WHAT_CLEAR,message);
        msg.sendToTarget();
    }

    private static void checkPermission(Context context) {
        String permission = "android.permission.SYSTEM_ALERT_WINDOW";
        int status = context.checkCallingOrSelfPermission(permission);
        if (status == PackageManager.PERMISSION_DENIED) {
            throw new IllegalStateException("in order to use LogPrinter, " +
                    "please add the permission " + permission + " to your AndroidManifest.xml");
        }
    }




    public static class Options implements Parcelable {

        public final int numberOfLines;
        public final int backgroundColor;
        public final int textColor;
        public final int textSize;

        /**
         * Contains options for LogPrinter. Defines
         *
         * @param builder
         */
        private Options(Builder builder) {
            numberOfLines = builder.numberOfLines;
            backgroundColor = builder.backgroundColor;
            textColor = builder.textColor;
            textSize = builder.textSize;
        }

        /**
         * Builder for {@link Options}
         */
        public static class Builder {
            private int numberOfLines = 1000;
            private int backgroundColor = 0xD993d2b9;
            private int textColor = 0xFFFFFFFF;
            private int textSize = 14;

            /**
             * @param n number of lines
             * @return
             */
            public Builder numberOfLines(int n) {
                ensurePositiveInt(n, "number of lines must be > 0");
                numberOfLines = n;
                return this;
            }

            /**
             * Sets the background color of the log messages
             *
             * @param color
             * @return
             */
            public Builder backgroundColor(int color) {
                backgroundColor = color;
                return this;
            }

            /**
             * Sets the text color of the log messages
             *
             * @param color
             * @return
             */
            public Builder textColor(int color) {
                textColor = color;
                return this;
            }

            /**
             * Sets the text size of the messages
             *
             * @param size
             * @return
             */
            public Builder textSize(int size) {
                ensurePositiveInt(size, "text size must be > 0");
                textSize = size;
                return this;
            }

            /**
             * Creates a {@link Options} with the customized parameters
             *
             * @return
             */
            public Options build() {
                return new Options(this);
            }
        }

        private static void ensurePositiveInt(int value, String msg) {
            if (value <= 0) {
                throw new IllegalArgumentException(msg);
            }
        }

        // Parcelable implementation
        private Options(Parcel source) {
            numberOfLines = source.readInt();
            backgroundColor = source.readInt();
            textColor = source.readInt();
            textSize = source.readInt();
        }

        public static final Creator<Options> CREATOR = new Creator<Options>() {
            @Override
            public Options createFromParcel(Parcel source) {
                return new Options(source);
            }

            @Override
            public Options[] newArray(int size) {
                return new Options[size];
            }
        };

        @Override
        public int describeContents() {
            return 0; // No special content.
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(numberOfLines);
            dest.writeInt(backgroundColor);
            dest.writeInt(textColor);
            dest.writeInt(textSize);
        }

    }

}
