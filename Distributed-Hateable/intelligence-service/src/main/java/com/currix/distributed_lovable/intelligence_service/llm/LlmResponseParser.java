package com.currix.distributed_lovable.intelligence_service.llm;

import com.currix.distributed_lovable.common_lib.enums.ChatEventStatus;
import com.currix.distributed_lovable.common_lib.enums.ChatEventType;
import com.currix.distributed_lovable.intelligence_service.entity.ChatEvent;
import com.currix.distributed_lovable.intelligence_service.entity.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class LlmResponseParser {

    /**
     * Regex Breakdown:
     * Group 1: Opening Tag (<tag ...>)
     * Group 2: Tag Name (message|file|tool)
     * Group 3: Attributes part (e.g., ' path="foo"' or ' args="a,b"')
     * Group 4: Content (The stuff inside)
     * Group 5: Closing Tag (</tag>)
     */

    private static final Pattern GENERIC_TAG_PATTERN = Pattern.compile(
            "<(message|file|tool)([^>]*)>(.*?)</\\1>",
            Pattern.CASE_INSENSITIVE | Pattern.DOTALL
    );

    // Helper to extract specific attributes (path="..." or args="...") from Group 3
    private static final Pattern ATTRIBUTE_PATTERN = Pattern.compile(
            "(path|args)\\s*=\\s*\"([^\"]+)\""
    );

    public List<ChatEvent> parseChatEvents(String fullResponse, ChatMessage parentMessage) {
        List<ChatEvent> events = new ArrayList<>();
        int orderCounter = 1;

        Matcher matcher = GENERIC_TAG_PATTERN.matcher(fullResponse);

        while (matcher.find()) {
            String tagName = matcher.group(1).toLowerCase();
            String attributes = matcher.group(2);
            String content = matcher.group(3);

            if (content != null) {
                content = content.trim();
            }

            // 🔥 DEBUG LOGS (ADD THIS)
            log.info("========== PARSER DEBUG ==========");
            log.info("TAG FOUND: {}", tagName);
            log.info("ATTRIBUTES RAW: {}", attributes);

            if (content != null) {
                log.info("CONTENT PREVIEW: {}", content.substring(0, Math.min(80, content.length())));
            } else {
                log.info("CONTENT IS NULL");
            }
            log.info("==================================");

            // Extract attributes map
            Map<String, String> attrMap = extractAttributes(attributes);

            ChatEvent.ChatEventBuilder builder = ChatEvent.builder()
                    .status(ChatEventStatus.CONFIRMED)
                    .chatMessage(parentMessage)
                    .content(content) // This is your Markdown content
                    .sequenceOrder(orderCounter++);

            switch (tagName) {
                case "message" -> builder.type(ChatEventType.MESSAGE);
                case "file" -> {

                    String path = attrMap.get("path");

                    // 🔥 VALIDATION
                    if (path == null || path.isBlank()) {
                        log.error("❌ FILE TAG WITHOUT PATH → SKIPPING");
                        continue;
                    }

                    if (content == null || content.isBlank()) {
                        log.error("❌ FILE TAG WITHOUT CONTENT → SKIPPING for path: {}", path);
                        continue;
                    }

                    // 🔥 CLEAN CONTENT
                    content = content.trim();

                    builder.type(ChatEventType.FILE_EDIT);
                    builder.status(ChatEventStatus.PENDING);
                    builder.filePath(path);
                    builder.content(content);

                    log.info("✅ FILE_EDIT DETECTED: {}", path);
                }
                case "tool" -> {
                    builder.type(ChatEventType.TOOL_LOG);
                    builder.metadata(attrMap.get("args")); // Store raw file list in metadata
                }
                default -> {
                    continue;
                }
            }

            events.add(builder.build());
        }

        return events;
    }
    private Map<String, String> extractAttributes(String attributeString) {
        Map<String, String> attributes = new HashMap<>();
        if (attributeString == null) return attributes;

        Matcher matcher = ATTRIBUTE_PATTERN.matcher(attributeString);
        while (matcher.find()) {
            attributes.put(matcher.group(1), matcher.group(2));
        }
        return attributes;
    }
}
