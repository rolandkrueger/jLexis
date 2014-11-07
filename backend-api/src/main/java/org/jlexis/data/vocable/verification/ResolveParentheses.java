package org.jlexis.data.vocable.verification;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.emptySet;
import static org.jlexis.util.StringUtils.isStringNullOrEmpty;

public final class ResolveParentheses {

    public static final String PARENTHESIZED_CONTENT_GROUP = "ParenthesizedContent";
    public static final String CONTENT_BEFORE_PARENTHESES_GROUP = "ContentBeforeParentheses";
    public static final String CONTENT_AFTER_PARENTHESES_GROUP = "ContentAfterParentheses";
    private static Pattern PATTERN = Pattern.compile(
                    "(?<ContentBeforeParentheses>.*?)" +
                    "\\((?<ParenthesizedContent>[^()]*)\\)" +
                    "(?<ContentAfterParentheses>.*?)");

    private ResolveParentheses() {
    }

    public static Set<String> resolveParentheses(String value) {
        if (isStringNullOrEmpty(value)) {
            return emptySet();
        }

        if (!value.contains("(")) {
            return Collections.singleton(value);
        }

        return new ResolveParentheses().resolve(value);
    }

    public static Set<String> resolveParenthesesForList(Collection<String> values) {
        if (values == null || values.isEmpty()) {
            return Collections.emptySet();
        }

        if (values.size() == 1) {
            return resolveParentheses(values.iterator().next());
        }

        HashSet<String> result = new HashSet<>();
        for (String value : values) {
            result.addAll(resolveParentheses(value));
        }
        return result;
    }

    private Set<String> resolve(String value) {
        if (isStringNullOrEmpty(value)) {
            return emptySet();
        }

        Set<String> result = new HashSet<>(Collections.singleton(value.trim()));
        String processedPrefix = "";
        int groupCount = 0;

        Matcher matcher = PATTERN.matcher(value);
        while (matcher.matches()) {
            if (groupCount > 3) {
                return Collections.singleton(value);
            }
            result.addAll(resolve(processedPrefix + matcher.group(CONTENT_BEFORE_PARENTHESES_GROUP) + matcher.group(CONTENT_AFTER_PARENTHESES_GROUP)));
            result.addAll(resolve(processedPrefix + matcher.group(CONTENT_BEFORE_PARENTHESES_GROUP) + matcher.group(PARENTHESIZED_CONTENT_GROUP) + matcher.group(CONTENT_AFTER_PARENTHESES_GROUP)));
            processedPrefix = value.substring(0, indexAfterClosingParentheses(matcher));
            matcher.region(indexAfterClosingParentheses(matcher), value.length());
            groupCount++;
        }

        return result;
    }

    private int indexAfterClosingParentheses(Matcher matcher) {
        return matcher.end(PARENTHESIZED_CONTENT_GROUP) + 1;
    }
}
