package com.company;


import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);
    public static String currencyFrom;
    public static String currencyTo;
    public static double amount;
    public static final String API_KEY = "849b590626msha2d99165166d1f6p162ec1jsnc3306391c1f1";


    public static void main(String[] args) throws IOException, InterruptedException {

        inputCurrencyFrom();

        inputCurrencyTo();

        inputAmount();

        String uri = "https://currency-converter5.p.rapidapi.com/currency/convert?format=json" +
                "&from=" + currencyFrom +
                "&to=" + currencyTo +
                "&amount=" + amount;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("x-rapidapi-key", API_KEY)
                .header("x-rapidapi-host", "currency-converter5.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

//        System.out.println(response.body());

        System.out.printf("%.2f %s = %.2f %s", amount, currencyFrom, Double.parseDouble(getRateForAmount(response.body())), currencyTo);

    }

    public static boolean isValidCurrency(String currency) {
        return currency.length() == 3;
    }

    public static String getRateForAmount(String response) {
        JSONObject json = new JSONObject(response);
        JSONObject rates = json.getJSONObject("rates");
        JSONObject currency = rates.getJSONObject(currencyTo);

        String rateForAmount = currency.getString("rate_for_amount");

        return rateForAmount;
    }

    public static void inputCurrencyFrom() {
        String currency;
        while (true) {
            System.out.println("Input currency u want to exchange FROM (USD, EUR, HUF,...):");
            currency = scanner.nextLine().toUpperCase();
            if (isValidCurrency(currency)) {
                break;
            }
        }
        currencyFrom = currency;
    }

    public static void inputCurrencyTo() {
        String currency;
        while (true) {
            System.out.println("Input currency u want to exchange TO (USD, EUR, HUF,...):");
            currency = scanner.nextLine().toUpperCase();
            if (isValidCurrency(currency)) {
                break;
            }
        }
        currencyTo = currency;
    }

    public static void inputAmount(){
        System.out.println("Input amount u want to exchange:");
        amount = Integer.parseInt(scanner.nextLine());
    }

}
