/**
 * This file is a component of Quartz Powered, this license makes sure any work
 * associated with Quartz Powered, must follow the conditions of the license included.
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Quartz Powered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.quartzpowered.protocol.data.chat.component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.quartzpowered.protocol.data.chat.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TranslatableComponent extends BaseComponent {

    private final ResourceBundle locales = ResourceBundle.getBundle("mojang-translations/en_US");
    private final Pattern format = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");

    /**
     * The key into the Minecraft locale files to use for the translation. The
     * text depends on the client's locale setting. The console is always en_US
     */
    private String translate;
    /**
     * The components to substitute into the translation
     */
    private List<BaseComponent> with;

    /**
     * Creates a translatable component from the original to clone it.
     *
     * @param original the original for the new translatable component.
     */
    public TranslatableComponent(TranslatableComponent original) {
        super(original);
        setTranslate(original.getTranslate());
        for (BaseComponent baseComponent : original.getWith()) {
            with.add(baseComponent.duplicate());
        }
    }

    /**
     * Creates a translatable component with the passed substitutions
     *
     * @param translate the translation key
     * @param with      the {@link String}s and
     *                  {@link BaseComponent}s to use into the
     *                  translation
     * @see #translate
     * @see #setWith(List)
     */
    public TranslatableComponent(String translate, Object... with) {
        setTranslate(translate);
        List<BaseComponent> temp = new ArrayList<BaseComponent>();
        for (Object w : with) {
            if (w instanceof String) {
                temp.add(new TextComponent((String) w));
            } else {
                temp.add((BaseComponent) w);
            }
        }
        setWith(temp);
    }

    /**
     * Creates a duplicate of this TranslatableComponent.
     *
     * @return the duplicate of this TranslatableComponent.
     */
    @Override
    public BaseComponent duplicate() {
        return new TranslatableComponent(this);
    }

    /**
     * Sets the translation substitutions to be used in this component. Removes
     * any previously set substitutions
     *
     * @param components the components to substitute
     */
    public void setWith(List<BaseComponent> components) {
        for (BaseComponent component : components) {
            component.parent = this;
        }
        with = components;
    }

    /**
     * Adds a text substitution to the component. The text will inherit this
     * component's formatting
     *
     * @param text the text to substitute
     */
    public void addWith(String text) {
        addWith(new TextComponent(text));
    }

    /**
     * Adds a component substitution to the component. The text will inherit
     * this component's formatting
     *
     * @param component the component to substitute
     */
    public void addWith(BaseComponent component) {
        if (with == null) {
            with = new ArrayList<BaseComponent>();
        }
        component.parent = this;
        with.add(component);
    }

    @Override
    protected void toPlainText(StringBuilder builder) {
        try {
            String trans = locales.getString(translate);
            Matcher matcher = format.matcher(trans);
            int position = 0;
            int i = 0;
            while (matcher.find(position)) {
                int pos = matcher.start();
                if (pos != position) {
                    builder.append(trans.substring(position, pos));
                }
                position = matcher.end();

                String formatCode = matcher.group(2);
                switch (formatCode.charAt(0)) {
                    case 's':
                    case 'd':
                        String withIndex = matcher.group(1);
                        with.get(withIndex != null ? Integer.parseInt(withIndex) - 1 : i++).toPlainText(builder);
                        break;
                    case '%':
                        builder.append('%');
                        break;
                }
            }
            if (trans.length() != position) {
                builder.append(trans.substring(position, trans.length()));
            }
        } catch (MissingResourceException e) {
            builder.append(translate);
        }

        super.toPlainText(builder);
    }

    @Override
    protected void toLegacyText(StringBuilder builder) {
        try {
            String trans = locales.getString(translate);
            Matcher matcher = format.matcher(trans);
            int position = 0;
            int i = 0;
            while (matcher.find(position)) {
                int pos = matcher.start();
                if (pos != position) {
                    addFormat(builder);
                    builder.append(trans.substring(position, pos));
                }
                position = matcher.end();

                String formatCode = matcher.group(2);
                switch (formatCode.charAt(0)) {
                    case 's':
                    case 'd':
                        String withIndex = matcher.group(1);
                        with.get(withIndex != null ? Integer.parseInt(withIndex) - 1 : i++).toLegacyText(builder);
                        break;
                    case '%':
                        addFormat(builder);
                        builder.append('%');
                        break;
                }
            }
            if (trans.length() != position) {
                addFormat(builder);
                builder.append(trans.substring(position, trans.length()));
            }
        } catch (MissingResourceException e) {
            addFormat(builder);
            builder.append(translate);
        }
        super.toLegacyText(builder);
    }

    private void addFormat(StringBuilder builder) {
        builder.append(getColor());
        if (isBold()) {
            builder.append(ChatColor.BOLD);
        }
        if (isItalic()) {
            builder.append(ChatColor.ITALIC);
        }
        if (isUnderlined()) {
            builder.append(ChatColor.UNDERLINE);
        }
        if (isStrikethrough()) {
            builder.append(ChatColor.STRIKETHROUGH);
        }
        if (isObfuscated()) {
            builder.append(ChatColor.MAGIC);
        }
    }
}
