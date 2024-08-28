/*
 * Copyright (c) 2024 Vladimir Marianciuc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *    all copies or substantial portions of the Software.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *     AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *     LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *     OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *      THE SOFTWARE.
 */

package io.github.marianciuc.streamingservice.subscription.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.github.marianciuc.jwtsecurity.service.JsonWebTokenService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures Feign for making requests to secured endpoints.
 *
 * <p>Requests made via Feign will automatically include an authorization
 * header set with a generated service token.</p>
 *
 */
@Configuration
@RequiredArgsConstructor
public class FeignConfiguration {


    /**
     * The service for generating JSON Web Tokens.
     */
    private final JsonWebTokenService jsonWebTokenService;


    /**
     * Intercepts outgoing Feign requests to add an authorization header.
     *
     * <p>The authorization header is set with a generated security token.</p>
     *
     * @return The request interceptor.
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {

            @Override
            public void apply(RequestTemplate requestTemplate) {
                requestTemplate.header(HttpHeaders.AUTHORIZATION, jsonWebTokenService.generateServiceToken());
            }
        };
    }
}
