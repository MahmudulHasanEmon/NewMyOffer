package com.holystock.newmyoffer.data.model;

import java.io.Serializable;

public class Confirm implements Serializable {

    private String requestType;
    private String headerTitle;
    private String subTitle;
    private Contact contact;
    private Body body;

    public Confirm() {
    }

    public Confirm(String requestType,
                   String headerTitle,
                   String subTitle,
                   Contact contact,
                   Body body) {
        this.requestType = requestType;
        this.headerTitle = headerTitle;
        this.subTitle = subTitle;
        this.contact = contact;
        this.body = body;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Confirm{" +
                "requestType='" + requestType + '\'' +
                ", headerTitle='" + headerTitle + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", contact=" + contact +
                ", body=" + body +
                '}';
    }

    public static class Body implements Serializable {

        private double amount;
        private double fee;
        private double charge;
        private double totalAmount;
        private double balance;
        private double newBalance;
        private String reference;

        public Body() {
        }

        public Body(double amount,
                    double fee,
                    double charge,
                    double totalAmount,
                    double balance,
                    double newBalance,
                    String reference) {

            this.amount = amount;
            this.fee = fee;
            this.charge = charge;
            this.totalAmount = totalAmount;
            this.balance = balance;
            this.newBalance = newBalance;
            this.reference = reference;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public double getFee() {
            return fee;
        }

        public void setFee(double fee) {
            this.fee = fee;
        }

        public double getCharge() {
            return charge;
        }

        public void setCharge(double charge) {
            this.charge = charge;
        }

        public double getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public double getNewBalance() {
            return newBalance;
        }

        public void setNewBalance(double newBalance) {
            this.newBalance = newBalance;
        }

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        @Override
        public String toString() {
            return "ConfirmBody{" +
                    "amount=" + amount +
                    ", fee=" + fee +
                    ", charge=" + charge +
                    ", totalAmount=" + totalAmount +
                    ", balance=" + balance +
                    ", newBalance=" + newBalance +
                    ", reference='" + reference + '\'' +
                    '}';
        }
    }
}