package com.duoc.enrollmentservice.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Asset {
    private byte[] bytes;
    private String name;
    private String contentType;
}