package com.devinfusion.journalApp.service;

import com.devinfusion.journalApp.entity.User;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class UserArgumentProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(User.builder().userName("shivi").password("shivi").build()),
                Arguments.of(User.builder().userName("chachu").password("").build())
        );
    }
}
