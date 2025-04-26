package com.example.contributorx_android;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Expectation {
    private int id = 0;
    private int contributorId = 0;
    private int contributionId = 0;
    private float amountPaid = 0.00f;
    private float amountToApprove = 0.00f;
    private int paymentStatus = 0;
    private String paymentReceipt = "";
    private Contributor contributor = null;
    private Contribution contribution = null;

    public Expectation() {
    }

    public Expectation(int contributorId, int contributionId, float amountPaid, float amountToApprove, int paymentStatus, String paymentReceipt) {
        this.contributorId = contributorId;
        this.contributionId = contributionId;
        this.amountPaid = amountPaid;
        this.amountToApprove = amountToApprove;
        this.paymentStatus = paymentStatus;
        this.paymentReceipt = paymentReceipt;
    }

    @JsonProperty("Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("ContributorId")
    public int getContributorId() {
        return contributorId;
    }

    public void setContributorId(int contributorId) {
        this.contributorId = contributorId;
    }

    @JsonProperty("ContributionId")
    public int getContributionId() {
        return contributionId;
    }

    public void setContributionId(int contributionId) {
        this.contributionId = contributionId;
    }

    @JsonProperty("AmountPaid")
    public float getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(float amountPaid) {
        this.amountPaid = amountPaid;
    }

    @JsonProperty("AmountToApprove")
    public float getAmountToApprove() {
        return amountToApprove;
    }

    public void setAmountToApprove(float amountToApprove) {
		this.amountToApprove = amountToApprove;
	}

    @JsonProperty("PaymentStatus")
    public int getPaymentStatus() {
		return paymentStatus;
	}

    public void setPaymentStatus(int paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

    @JsonProperty("PaymentReceipt")
    public String getPaymentReceipt() {
		return paymentReceipt;
	}

    public void setPaymentReceipt(String paymentReceipt) {
		this.paymentReceipt = paymentReceipt;
	}

    @JsonProperty("Contributor")
    public Contributor getContributor() { return contributor; }

    public void setContributor(Contributor contributor) {
        this.contributor = contributor;
    }

    @JsonProperty("Contribution")
    public Contribution getContribution() { return contribution; }

    public void setContribution(Contribution contribution) {
        this.contribution = contribution;
    }
}
