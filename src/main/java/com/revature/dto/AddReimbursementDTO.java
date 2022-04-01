package com.revature.dto;

import io.javalin.http.UploadedFile;

import java.util.Objects;

public class AddReimbursementDTO {

    private int amount;
    private String submitted;
    private String description;
    private UploadedFile receipt;
    private int type;

    public AddReimbursementDTO() {
    }

    public AddReimbursementDTO(int amount, String submitted, String description, UploadedFile receipt, int type) {
        this.amount = amount;
        this.submitted = submitted;
        this.description = description;
        this.receipt = receipt;
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getSubmitted() {
        return submitted;
    }

    public void setSubmitted(String submitted) {
        this.submitted = submitted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UploadedFile getReceipt() {
        return receipt;
    }

    public void setReceipt(UploadedFile receipt) {
        this.receipt = receipt;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddReimbursementDTO that = (AddReimbursementDTO) o;
        return amount == that.amount && type == that.type && Objects.equals(submitted, that.submitted) && Objects.equals(description, that.description) && Objects.equals(receipt, that.receipt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, submitted, description, receipt, type);
    }

    @Override
    public String toString() {
        return "AddReimbursementDTO{" +
                "amount=" + amount +
                ", submitted='" + submitted + '\'' +
                ", description='" + description + '\'' +
                ", receipt=" + receipt +
                ", type=" + type +
                '}';
    }
}
