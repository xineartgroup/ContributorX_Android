package com.example.contributorx_android;

import java.util.ArrayList;
import java.util.List;

public class _DAO_Contribution {

    private static List<Contribution> contributions = new ArrayList<>();

    public static List<Contribution> GetAllContributions() {

        return contributions;

    }

    public static int AddContribution(Contribution contribution) {
        contribution.setId(contributions.isEmpty() ? 1 : contributions.get(contributions.size() - 1).getId() + 1);
        contributions.add(contribution);
        return contribution.getId();
    }

    public static void UpdateContribution(Contribution contribution) {
        for (int index = 0; index < contributions.size(); index++) {
            if (contributions.get(index).getId() == contribution.getId()) {
                contributions.set(index, contribution);
                return;
            }
        }
    }

    public static void DeleteContribution(int id) {
        for (int index = 0; index < contributions.size(); index++) {
            if (contributions.get(index).getId() == id) {
                contributions.remove(index);
                return;
            }
        }
    }

    public static Contribution GetContribution(int id) {
        for (int index = 0; index < contributions.size(); index++) {
            if (contributions.get(index).getId() == id) {
                return contributions.get(index);
            }
        }
        return null;
    }
}
