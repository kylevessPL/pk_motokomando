package pl.motokomando.healthcare.controller.base;

import pl.motokomando.healthcare.model.base.BaseModel;

public class BaseController {

    private final BaseModel model;

    public BaseController(BaseModel model) {
        this.model = model;
    }

}
