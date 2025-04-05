package com.example.contributorx_android;

import java.util.ArrayList;
import java.util.List;

public class _DAO_Contributor {

    private static List<Contributor> contributors = new ArrayList<>();

    public static List<Contributor> GetAllContributors() {
        return contributors;
    }

    public static int AddContributor(Contributor contributor) {
        contributor.setId(contributors.isEmpty() ? 1 : contributors.get(contributors.size() - 1).getId() + 1);
        contributors.add(contributor);
        return contributor.getId();
    }

    public static void UpdateContributor(Contributor contributor) {
        for (int index = 0; index < contributors.size(); index++) {
            if (contributors.get(index).getId() == contributor.getId()) {
                contributors.set(index, contributor);
                return;
            }
        }
    }

    public static void DeleteContributor(int id) {
        for (int index = 0; index < contributors.size(); index++) {
            if (contributors.get(index).getId() == id) {
                contributors.remove(index);
                return;
            }
        }
    }

    public static Contributor GetContributor(int id) {
        for (int index = 0; index < contributors.size(); index++) {
            if (contributors.get(index).getId() == id) {
                return contributors.get(index);
            }
        }

        return null;
    }

    public static Contributor GetContributorByUsername(String username) {
        for (int i = 0; i < contributors.size(); i++) {
            Contributor contributor = contributors.get(i);
            if (contributor != null && contributor.getUserName().equals(username)) {
                return contributor;
            }
        }

        return null;
    }

    public static List<Contributor> SearchContributors(String strName) {
        List<Contributor> result = new ArrayList<>();

        if ("".equals(strName)) {
            return contributors;
        }
        else {
            for (int i = 0; i < contributors.size(); i++) {
                if (contributors.get(i).getFirstname().contains(strName) ||
                        contributors.get(i).getLastname().contains(strName)) {
                    result.add(contributors.get(i));
                }
            }
        }

        return result;
    }

    public static List<Contributor> GetActive() {
        List<Contributor> result = new ArrayList<>();

        for (int i = 0; i < contributors.size(); i++) {
            if (contributors.get(i).isActive()) {
                result.add(contributors.get(i));
            }
        }

        return result;
    }

    public static List<Contributor> GetInActive() {
        List<Contributor> result = new ArrayList<>();

        for (int i = 0; i < contributors.size(); i++) {
            if (!contributors.get(i).isActive()) {
                result.add(contributors.get(i));
            }
        }

        return result;
    }

    public static List<Contributor> GetContributorsInCommunity(int communityId) {
        List<Contributor> result = new ArrayList<>();

        for (int i = 0; i < contributors.size(); i++) {
            if (contributors.get(i).getCommunityId() == communityId) {
                result.add(contributors.get(i));
            }
        }

        return result;
    }
}
