package com.example.newstockMarkt;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String dateToStr(String format, Date date) {
        SimpleDateFormat simpleDate = new SimpleDateFormat(format);
        return simpleDate.format(date);
    }

    public static String getNewsApiUrl(String date, int pageIndex) {
        return String.format("https://newsapi.org/v2/everything?q=stock&from=%s&sortBy=publishedAt&apiKey=66795718384c4a95b04124b4aa4caf0c&page=%d", date, pageIndex);
    }

    public static String getCompanyNewsAPi(String date, String company) {
        return String.format("https://newsapi.org/v2/everything?q=%s&from=%s&sortBy=publishedAt&apiKey=66795718384c4a95b04124b4aa4caf0c&page=%d", company, date);
    }

    public static String getSearchApi(String searchTerm) {
        return String.format("https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords=%s&apikey=CPCVBBAFLKBYDBVV", searchTerm);
    }

    public static String getStockPriceSeriesApi(String symbol) {
        return String.format("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=%s&apikey=CPCVBBAFLKBYDBVV", symbol);
    }

    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
