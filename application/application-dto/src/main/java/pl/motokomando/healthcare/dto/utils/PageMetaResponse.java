package pl.motokomando.healthcare.dto.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PageMetaResponse {

    private boolean isFirst;
    private boolean isLast;
    private boolean hasPrev;
    private boolean hasNext;
    private Integer currentPage;
    private Integer totalPage;
    private Long totalCount;
    
}
