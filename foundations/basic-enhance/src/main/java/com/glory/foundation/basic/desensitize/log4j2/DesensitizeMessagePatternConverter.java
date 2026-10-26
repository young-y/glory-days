/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.foundation.basic.desensitize.log4j2;

import com.glory.foundation.basic.desensitize.DesensitizeHelper;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.*;
import org.apache.logging.log4j.core.util.Loader;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MultiformatMessage;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.MultiFormatStringBuilderFormattable;
import org.apache.logging.log4j.util.PerformanceSensitive;
import org.apache.logging.log4j.util.StringBuilderFormattable;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.helpers.MessageFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.apache.logging.log4j.util.Strings.toRootUpperCase;

/**
 * @author YY
 * @date 2026/10/25
 * @description: TODO
 */
@Plugin(name = "DesensitizeMessagePatternConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({"dm", "dmsg"})
@PerformanceSensitive("allocation")
public class DesensitizeMessagePatternConverter extends LogEventPatternConverter {
    private static final String LOOKUPS = "lookups";
    private static final String NOLOOKUPS = "nolookups";

    /**
     * Constructs an instance of LoggingEventPatternConverter.
     */
    private DesensitizeMessagePatternConverter() {
        super("Desensitize", "desensitize");
    }
    private static TextRenderer loadMessageRenderer(final String[] options) {
        if (options != null) {
            for (final String option : options) {
                switch (toRootUpperCase(option)) {
                    case "TEXT":
                        if (Loader.isJansiAvailable()) {
                            return new PlainTextRenderer();
                        }
                        StatusLogger.getLogger()
                                .warn("You requested ANSI message rendering but JANSI is not on the classpath.");
                        return null;
                    case "HTML":
                        return new HtmlTextRenderer(options);
                }
            }
        }
        return null;
    }
    public static DesensitizeMessagePatternConverter newInstance(final Configuration config, final String[] options) {
        final String[] formats = withoutLookupOptions(options);
        final TextRenderer textRenderer = loadMessageRenderer(formats);
        DesensitizeMessagePatternConverter result = formats == null || formats.length == 0
                ? DesensitizeMessagePatternConverter.SimpleMessagePatternConverter.INSTANCE
                : new DesensitizeMessagePatternConverter.FormattedMessagePatternConverter(formats);
        if (textRenderer != null) {
            result = new DesensitizeMessagePatternConverter.RenderingPatternConverter(result, textRenderer);
        }
        return result;
    }
    private static String[] withoutLookupOptions(final String[] options) {
        if (options == null || options.length == 0) {
            return options;
        }
        final List<String> results = new ArrayList<>(options.length);
        for (String option : options) {
            if (LOOKUPS.equalsIgnoreCase(option) || NOLOOKUPS.equalsIgnoreCase(option)) {
                LOGGER.info("The {} option will be ignored. Message Lookups are no longer supported.", option);
            } else {
                results.add(option);
            }
        }
        return results.toArray(Strings.EMPTY_ARRAY);
    }

    @Override
    public void format(LogEvent event, StringBuilder toAppendTo) {
        throw new UnsupportedOperationException();
    }


    private static final class SimpleMessagePatternConverter extends DesensitizeMessagePatternConverter {
        private static final DesensitizeMessagePatternConverter INSTANCE = new DesensitizeMessagePatternConverter.SimpleMessagePatternConverter();

        /**
         * {@inheritDoc}
         */
        @Override
        public void format(final LogEvent event, final StringBuilder toAppendTo) {
            final Message msg = event.getMessage();
            if (null != msg){
                Object[] parameters = msg.getParameters();
                if (parameters != null && parameters.length > 0){
                    Object[] array = Arrays.stream(parameters).map(DesensitizeHelper::desensitizeToJson).toArray();
                    toAppendTo.append(MessageFormatter.arrayFormat(msg.getFormat(), array).getMessage());

                }else {
                    if (msg instanceof StringBuilderFormattable) {
                        ((StringBuilderFormattable) msg).formatTo(toAppendTo);
                    } else {
                        toAppendTo.append(msg.getFormattedMessage());
                    }
                }
            }
        }
    }

    private static final class FormattedMessagePatternConverter extends DesensitizeMessagePatternConverter {

        private final String[] formats;

        FormattedMessagePatternConverter(final String[] formats) {
            this.formats = formats;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void format(final LogEvent event, final StringBuilder toAppendTo) {
            final Message msg = event.getMessage();
            if (msg instanceof StringBuilderFormattable) {
                if (msg instanceof MultiFormatStringBuilderFormattable) {
                    ((MultiFormatStringBuilderFormattable) msg).formatTo(formats, toAppendTo);
                } else {
                    ((StringBuilderFormattable) msg).formatTo(toAppendTo);
                }
            } else if (msg != null) {
                toAppendTo.append(
                        msg instanceof MultiformatMessage
                                ? ((MultiformatMessage) msg).getFormattedMessage(formats)
                                : msg.getFormattedMessage());
            }
        }
    }

    private static final class RenderingPatternConverter extends DesensitizeMessagePatternConverter {

        private final DesensitizeMessagePatternConverter delegate;
        private final TextRenderer textRenderer;

        RenderingPatternConverter(final DesensitizeMessagePatternConverter delegate, final TextRenderer textRenderer) {
            this.delegate = delegate;
            this.textRenderer = textRenderer;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void format(final LogEvent event, final StringBuilder toAppendTo) {
            final StringBuilder workingBuilder = new StringBuilder(80);
            delegate.format(event, workingBuilder);
            textRenderer.render(workingBuilder, toAppendTo);
        }
    }

}
