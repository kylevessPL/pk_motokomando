package pl.motokomando.healthcare.api.utils;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class PageResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, @Nullable Class<? extends HttpMessageConverter<?>> converterType) {
        return PageResponse.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            @Nullable MethodParameter returnType,
            @Nullable MediaType selectedContentType,
            @Nullable Class<? extends HttpMessageConverter<?>> selectedConverterType,
            @Nullable ServerHttpRequest request,
            ServerHttpResponse response) {
        PageResponse<?> pageResponse = (PageResponse<?>) body;
        response.getHeaders().add("X-Count-Per-Page", String.valueOf(pageResponse.getPageSize()));
        response.getHeaders().add("X-Current-Page", String.valueOf(pageResponse.getPage()));
        response.getHeaders().add("X-Total-Count", String.valueOf(pageResponse.getTotalCount()));
        response.getHeaders().add("X-Total-Pages", String.valueOf(pageResponse.getTotalPage()));
        return pageResponse.getContent();
    }

}
