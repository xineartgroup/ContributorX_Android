package com.example.contributorx_android;

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
            if (expectations.get(i).getContributorId() == contributorId) {
                result.add(expectations.get(i));
            }
        }

        return result;
    }
}
