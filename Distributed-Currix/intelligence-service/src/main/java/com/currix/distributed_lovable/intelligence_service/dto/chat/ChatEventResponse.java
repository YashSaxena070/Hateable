package com.currix.distributed_lovable.intelligence_service.dto.chat;


import com.currix.distributed_lovable.common_lib.enums.ChatEventType;

public record ChatEventResponse (
    Long id,

    ChatEventType type,

    Integer sequenceOrder,

    String content,

    String filePath, // NULL unless FILE_EDIT

    String metadata
    ){
}
