package pl.motokomando.healthcare.api.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@ApiModel
@RequiredArgsConstructor
@Getter
public final class PageMeta {

    @ApiModelProperty(value = "Page number", example = "1")
    private final Integer page;
    @ApiModelProperty(value = "Page size", example = "10")
    private final Integer pageSize;
    @ApiModelProperty(value = "Total pages", example = "30")
    private final Integer totalPage;

}
