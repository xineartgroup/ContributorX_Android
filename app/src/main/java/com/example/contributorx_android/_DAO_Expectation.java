package com.example.contributorx_android;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class _DAO_Expectation {

    private static int lastId = 0;

    private static List<Expectation> expectations = new ArrayList<>();

    public static List<Expectation> GetAllExpectations() {
        return expectations;
    }

    public static int AddExpectation(Expectation expectation) {
        expectation.setId(++lastId);
        expectations.add(expectation);
        return expectation.getId();
    }

    public static void UpdateExpectation(Expectation expectation) {
        for (int index = 0; index < expectations.size(); index++) {
            if (expectations.get(index).getId() == expectation.getId()) {
                expectations.set(index, expectation);
                return;
            }
        }
    }

    public static void DeleteExpectation(int id) {
        for (int index = 0; index < expectations.size(); index++) {
            if (expectations.get(index).getId() == id) {
                    expectations.remove(index);
                    return;
            }
        }
    }

    public static String GetExpectationString(int Id) {
        try {
            URL url = new URL("http://192.168.0.123:3000/expectation/api/" + Id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return response.toString();
            } else {
                return "GET request failed";
            }
        } catch (Exception e) {
            return e.toString();
        }
    }

    public static Expectation GetExpectation(int Id) {
        for (int i = 0; i < expectations.size(); i++) {
            if (expectations.get(i).getId() == Id) {
                return expectations.get(i);
            }
        }

        return null;
    }

    public static List<Expectation> GetExpectationsForContributor(int contributorId) {
        List<Expectation> result = new ArrayList<>();

        for (int i = 0; i < expectations.size(); i++) {
            Expectation expectation = expectations.get(i);
            if (expectation.getContributorId() == contributorId) {
                if (expectation.getPaymentStatus() != 2 && expectation.getPaymentStatus() != 3) {
                    result.add(expectation);
                }
            }
        }

        return result;
    }

    public static List<Expectation> GetUnclearedExpectationsInCommunity(int communityId) {

        List<Expectation> result = new ArrayList<>();

        for (int i = 0; i < expectations.size(); i++) {
            Expectation expectation = expectations.get(i);
            Contributor contributor = _DAO_Contributor.GetContributor(expectation.getContributorId());
            Contribution contribution = _DAO_Contribution.GetContribution(expectation.getContributionId());
            if (contributor != null && contribution != null && contributor.getCommunityId() == communityId) {
                if (expectation.getPaymentStatus() != 2 && expectation.getPaymentStatus() != 3 &&
                        contribution.getAmount() - expectation.getAmountPaid() > 0) {
                    result.add(expectation);
                }
            }
        }

        return result;
    }
}
