package com.oguzarapkirli.socialnetworkexample.util.converter;

import com.oguzarapkirli.socialnetworkexample.util.enums.StoryType;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

public class StringToStoryTypeConverter implements Converter<String, StoryType> {

    @Override
    public StoryType convert(String source) {
        return EnumUtils.isValidEnum(StoryType.class, StringUtils.upperCase(source)) ? StoryType.valueOf(StringUtils.upperCase(source)) : null;
    }
}
