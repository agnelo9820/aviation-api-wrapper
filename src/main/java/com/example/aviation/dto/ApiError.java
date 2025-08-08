package com.example.aviation.dto;

public record ApiError(int status, String message, String timestamp) {}