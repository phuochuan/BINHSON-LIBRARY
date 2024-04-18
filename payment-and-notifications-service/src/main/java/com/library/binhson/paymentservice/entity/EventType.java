package com.library.binhson.paymentservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public enum EventType {
    BOOK_RESERVATION_CONFIRMATION("Book Reservation Confirmation"),
    BOOK_DUE_REMINDER("Book Due Reminder"),
    OVERDUE_BOOK_NOTICE("Overdue Book Notice"),
    BOOK_AVAILABILITY_NOTIFICATION("Book Availability Notification"),
    LIBRARY_EVENT_ANNOUNCEMENT("Library Event Announcement"),
    LIBRARY_CLOSURE_NOTICE("Library Closure Notice"),
    ACCOUNT_ACTIVITY_SUMMARY("Account Activity Summary"),
    LIBRARY_POLICY_UPDATES("Library Policy Updates"),
    LIBRARY_NEWSLETTER("Library Newsletter"),
    BOOK_RETURN_CONFIRMATION("Book Return Confirmation"),
    BIRTHDAY("Birthday");

    private final String description;

    EventType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

