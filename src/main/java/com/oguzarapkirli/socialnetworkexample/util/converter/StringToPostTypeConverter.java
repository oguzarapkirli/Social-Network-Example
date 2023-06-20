package com.oguzarapkirli.socialnetworkexample.util.converter;

import com.oguzarapkirli.socialnetworkexample.util.enums.PostType;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

public class StringToPostTypeConverter implements Converter<String, PostType> {

    @Override
    public PostType convert(String source) {
        return EnumUtils.isValidEnum(PostType.class, StringUtils.upperCase(source)) ? PostType.valueOf(StringUtils.upperCase(source)) : null;
    }
}
