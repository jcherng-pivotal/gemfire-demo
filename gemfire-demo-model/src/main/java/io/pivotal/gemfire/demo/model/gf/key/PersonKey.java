package io.pivotal.gemfire.demo.model.gf.key;

import java.io.Serializable;

/**
 * Created by zhansen on 3/16/17.
 */

public class PersonKey implements Serializable {
    int id;

    public PersonKey() {
    }

    public PersonKey(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonKey personKey = (PersonKey) o;

        return id == personKey.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
