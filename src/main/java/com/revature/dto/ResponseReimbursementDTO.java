package com.revature.dto;

import java.util.Objects;

public class ResponseReimbursementDTO {

    private int id;
    private int amount;
    private String submitted;
    private String resolved;
    private String description;
    private String receiptUrl;
    private String authorUsername;
    private String resolverUsername;
    private String status;
    private String type;

    public ResponseReimbursementDTO() {
    }

    public ResponseReimbursementDTO(int id, int amount, String submitted, String resolved, String description, String receiptUrl, String authorUsername, String resolverUsername, String status, String type) {
        this.id = id;
        this.amount = amount;
        this.submitted = submitted;
        this.resolved = resolved;
        this.description = description;
        this.receiptUrl = receiptUrl;
        this.authorUsername = authorUsername;
        this.resolverUsername = resolverUsername;
        this.status = status;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getResolved() {
        return resolved;
    }

    public void setResolved(String resolved) {
        this.resolved = resolved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReceiptUrl() {
        return receiptUrl;
    }

    public void setReceiptUrl(String receiptUrl) {
        this.receiptUrl = receiptUrl;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public String getResolverUsername() {
        return resolverUsername;
    }

    public void setResolverUsername(String resolverUsername) {
        this.resolverUsername = resolverUsername;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseReimbursementDTO that = (ResponseReimbursementDTO) o;
        return id == that.id && amount == that.amount && Objects.equals(submitted, that.submitted) && Objects.equals(resolved, that.resolved) && Objects.equals(description, that.description) && Objects.equals(receiptUrl, that.receiptUrl) && Objects.equals(authorUsername, that.authorUsername) && Objects.equals(resolverUsername, that.resolverUsername) && Objects.equals(status, that.status) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, submitted, resolved, description, receiptUrl, authorUsername, resolverUsername, status, type);
    }

    @Override
    public String toString() {
        return "ResponseReimbursementDTO{" +
                "id=" + id +
                ", amount=" + amount +
                ", submitted='" + submitted + '\'' +
                ", resolved='" + resolved + '\'' +
                ", description='" + description + '\'' +
                ", receiptUrl='" + receiptUrl + '\'' +
                ", authorUsername='" + authorUsername + '\'' +
                ", resolverUsername='" + resolverUsername + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
