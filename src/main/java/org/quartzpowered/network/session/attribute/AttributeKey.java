package org.quartzpowered.network.session.attribute;

public class AttributeKey<T> {
    private AttributeKey() {
    }

    public static <T> AttributeKey<T> create() {
        return new AttributeKey<>();
    }
}
