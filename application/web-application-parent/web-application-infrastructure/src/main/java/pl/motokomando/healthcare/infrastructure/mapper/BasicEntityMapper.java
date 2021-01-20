package pl.motokomando.healthcare.infrastructure.mapper;

import org.springframework.stereotype.Component;
import pl.motokomando.healthcare.domain.model.utils.Basic;

@Component
public class BasicEntityMapper {

    public Basic mapToBasic(Integer id) {
        return createBasic(id);
    }

    private Basic createBasic(Integer id) {
        return new Basic(id);
    }

}
