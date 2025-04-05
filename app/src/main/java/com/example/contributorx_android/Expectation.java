package com.example.contributorx_android;

public class Expectation {
    private int id = 0;
    private int contributorId = 0;
    private int contributionId = 0;
    private float amountPaid = 0.00f;
    private float amountToApprove = 0.00f;
    private int paymentStatus = 0;
    private String paymentReceipt = "";

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContributorId() {
        return contributorId;
    }

    public void setContributorId(int contributorId) {
        this.contributorId = contributorId;
    }

    public int getContributionId() {
        return contributionId;
    }

    public void setContributionId(int contributionId) {
        this.contributionId = contributionId;
    }

    public float getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(float amountPaid) {
        this.amountPaid = amountPaid;
    }

    public float getAmountToApprove() {
        return amountToApprove;
    }

    public void setAmountToApprove(float amountToApprove) {
		this.amountToApprove = amountToApprove;
	}

    public int getPaymentStatus() {
		return paymentStatus;
	}

    public void setPaymentStatus(int paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

    public String getPaymentReceipt() {
		return paymentReceipt;
	}

    public void setPaymentReceipt(String paymentReceipt) {
		this.paymentReceipt = paymentReceipt;
	}
}
