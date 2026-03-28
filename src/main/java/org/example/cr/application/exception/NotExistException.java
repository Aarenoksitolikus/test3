package org.example.cr.application.exception;

import org.example.cr.api.dto.ApiErrorResponseDto;


public class NotExistException extends RuntimeException {
    private ApiErrorResponseDto apiErrorResponseDto;

    public NotExistException(ApiErrorResponseDto apiErrorResponseDto){
        super(apiErrorResponseDto.getDescription());
        this.apiErrorResponseDto = apiErrorResponseDto;
    }

    public ApiErrorResponseDto getApiErrorResponseDto(){
        return this.apiErrorResponseDto;
    }
}
