package pl.motokomando.healthcare.domain.model.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BasicPagedQueryCommand {

    private Integer page;
    private Integer size;
    private String sortBy;
    private SortDirection sortDir;

}
