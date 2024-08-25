package com.mv.streamingservice.order.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JsonWebTokenService {
    @Value("${jwt.secret-key}")
    private String secretKey;


}
