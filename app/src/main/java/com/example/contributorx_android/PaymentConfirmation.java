package com.example.contributorx_android;

public class PaymentConfirmation {
    private int contributorId = 0;
    private float amountPaid = 0.00f;

    public PaymentConfirmation() {
        this.contributorId = -1;
        this.amountPaid = 0.00f;
    }

    public PaymentConfirmation(int contributorId, float amountPaid) {
        this.contributorId = contributorId;
        this.amountPaid = amountPaid;
    }

    public int getContributorId() {
        return contributorId;
    }

    public void setContributorId(int contributorId) {
        this.contributorId = contributorId;
    }

    public float getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(float amountPaid) {
        this.amountPaid = amountPaid;
    }
}
