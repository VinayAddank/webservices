package org.rta.core.model.common;

import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;

import org.rta.core.model.base.BaseModel;

public class ModelList<T> extends BaseModel {

    private static final long serialVersionUID = -4429443117646695158L;

    private List<T> list;

    public ModelList() {}

    public ModelList(List<T> list) {
        this.list = list;
    }

    @XmlAnyElement(lax = true)
    public List<T> getList() {
        return list;
    }
}
