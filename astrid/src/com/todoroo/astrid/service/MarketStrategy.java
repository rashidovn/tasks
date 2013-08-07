/**
 * Copyright (c) 2012 Todoroo Inc
 *
 * See the file "LICENSE" for the full license governing this code.
 */
package com.todoroo.astrid.service;

import android.content.Intent;
import android.net.Uri;

import org.astrid.R;

public abstract class MarketStrategy {

    /**
     * @param packageName
     * @return an intent to launch market with this package
     */
    abstract public Intent generateMarketLink(String packageName);

    abstract public String strategyId();

    public int[] excludedSettings() {
        return null;
    }

    /**
     * Return true if the preference to use the phone layout should be
     * turned on by default (only true for Nook)
     *
     * @return
     */
    public boolean defaultPhoneLayout() {
        return false;
    }

    public static class AndroidMarketStrategy extends MarketStrategy {

        @Override
        public Intent generateMarketLink(String packageName) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://search?q=pname:" + //$NON-NLS-1$
                            packageName));
        }

        @Override
        public String strategyId() {
            return "android_market"; //$NON-NLS-1$
        }
    }

    public static class AmazonMarketStrategy extends MarketStrategy {

        @Override
        public Intent generateMarketLink(String packageName) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=" + //$NON-NLS-1$
                            packageName));
        }

        /**
         * @return true if the device is a kindle fire and needs special treatment
         */
        public static boolean isKindleFire() {
            return android.os.Build.MANUFACTURER.equals("Amazon") && //$NON-NLS-1$
                    android.os.Build.MODEL.contains("Kindle"); //$NON-NLS-1$
        }

        @Override
        public int[] excludedSettings() {
            return new int[]{
                    R.string.p_theme_widget,
                    R.string.p_voicePrefSection,
                    R.string.p_end_at_deadline,
                    R.string.p_field_missed_calls
            };
        }

        @Override
        public String strategyId() {
            return "amazon_market"; //$NON-NLS-1$
        }

    }

    public static class NookMarketStrategy extends MarketStrategy {

        @Override
        public Intent generateMarketLink(String packageName) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://search?q=pname:" + //$NON-NLS-1$
                            packageName));
        }

        @Override
        public int[] excludedSettings() {
            return new int[]{
                    R.string.p_theme_widget,
                    R.string.p_voicePrefSection,
                    R.string.p_end_at_deadline,
                    R.string.p_field_missed_calls,
                    R.string.p_rmd_vibrate,
                    R.string.gcal_p_default,
                    R.string.p_theme_widget,
                    R.string.p_voiceInputEnabled,
                    R.string.p_voiceInputCreatesTask,
                    R.string.p_use_contact_picker
            };
        }

        @Override
        public boolean defaultPhoneLayout() {
            return true;
        }

        @Override
        public String strategyId() {
            return "nook_market"; //$NON-NLS-1$
        }

    }

}
