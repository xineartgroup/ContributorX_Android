package com.example.contributorx_android;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class _DAO_Contributor {

    private static int lastId = 0;

    private static List<Contributor> contributors = new ArrayList<>();

    public static List<Contributor> GetAllContributors() {
        return contributors;
    }

    public static int AddContributor(Contributor contributor) {
        contributor.setId(++lastId);
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

    public static APIContributorResponse DeleteContributor(int id) {
        String result = APIClass.SendMessage("POST", "contributor/api/delete/" + id,"", "", false);
        return APIClass.GetContributorResponse(result);
    }

    public static APIContributorResponse GetContributor(int id) {
        String result = APIClass.SendMessage("GET", "contributor/api/" + id,"", "", false);
        return APIClass.GetContributorResponse(result);
    }

    public static APIContributorsResponse GetContributorsInCommunity(int communityId) {
        String result = APIClass.SendMessage("GET", "contributor/api?communityid=" + communityId + String.format("&searchValue=%s&sortName=%s&sortOrder=%s", "*", "Id", "ASC"),"", "", false);
        return APIClass.GetContributorsResponse(result);
    }
}
