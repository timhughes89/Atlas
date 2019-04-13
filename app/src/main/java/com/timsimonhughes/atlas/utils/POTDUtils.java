package com.timsimonhughes.atlas.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class POTDUtils {

    private static SimpleDateFormat utcFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMMM yy", Locale.getDefault());

    public static String formatDate(String unformattedDate) {
        String formattedDate = null;

        try {
            Date startDate = utcFormatter.parse(unformattedDate);
            formattedDate = dateFormatter.format(startDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }


}
