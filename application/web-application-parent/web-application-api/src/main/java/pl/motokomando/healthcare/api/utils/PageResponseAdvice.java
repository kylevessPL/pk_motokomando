package pl.motokomando.healthcare.api.utils;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

@ControllerAdvice
public class PageResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, @Nullable Class<? extends HttpMessageConverter<?>> converterType) {
        return PageResponse.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public Object beforeBodyWrite(
            @Nullable Object body,
            @Nullable MethodParameter returnType,
            @Nullable MediaType selectedContentType,
            @Nullable Class<? extends HttpMessageConverter<?>> selectedConverterType,
            @Nullable ServerHttpRequest request,
            ServerHttpResponse response) {
        PageResponse<?> pageResponse = (PageResponse<?>) body;
        PageMeta meta = Objects.requireNonNull(pageResponse).getMeta();
        response.getHeaders().add("X-Paging-Page", String.valueOf(meta.getPage()));
        response.getHeaders().add("X-Paging-Page-Size", String.valueOf(meta.getPageSize()));
        response.getHeaders().add("X-Paging-Total-Page", String.valueOf(meta.getTotalPage()));
        return pageResponse.getContent();
    }

}
