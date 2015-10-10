package com.king.backend.server.endpoint;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexURIMatcher implements URIMatcher {

    private final Pattern pattern;

    public RegexURIMatcher(String pathExpression) {
        super();
        this.pattern = Pattern.compile(pathExpression);
    }

    @Override
    public boolean match(URI uri) {
        Matcher matcher = pattern.matcher(uri.getPath());
        return matcher.matches();

    }

}
