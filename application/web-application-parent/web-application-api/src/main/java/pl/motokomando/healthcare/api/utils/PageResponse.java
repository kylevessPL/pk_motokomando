package pl.motokomando.healthcare.api.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ApiModel
@RequiredArgsConstructor
@Getter
public final class PageResponse<T> {

    @ApiModelProperty(value = "Page metadata")
    private final PageMeta meta;
    @ApiModelProperty(value = "Result content")
    private final List<T> content;

}
