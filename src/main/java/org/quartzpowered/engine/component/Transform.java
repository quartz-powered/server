package org.quartzpowered.engine.component;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Transform extends Component {
    private final List<Transform> children = new ArrayList<>();

    @Getter
    private Transform parent;

    public Collection<Transform> getChildren() {
        return Collections.unmodifiableCollection(children);
    }

    public void setParent(Transform parent) {
        if (this.parent != null) {
            this.parent.children.remove(this);
        }

        this.parent = parent;
        this.parent.children.add(this);
    }

}
