package com.zipte.platform.server.domain.review;

public class ReviewSnippetFixtures {
    public static ReviewSnippet stub() {
        return ReviewSnippet.builder()
                .title("title")
                .content("content")
                .apartmentManagement(1)
                .environment(1)
                .livingEnvironment(1)
                .transport(1)
                .overall(1)
                .build();
    }

    public static ReviewSnippet anotherStub() {
        return ReviewSnippet.builder()
                .title("title2")
                .content("content2")
                .apartmentManagement(0)
                .environment(2)
                .livingEnvironment(0)
                .transport(2)
                .overall(2)
                .build();
    }

}
