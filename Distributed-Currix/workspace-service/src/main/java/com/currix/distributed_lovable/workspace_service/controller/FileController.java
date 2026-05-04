package com.currix.distributed_lovable.workspace_service.controller;

import com.currix.distributed_lovable.common_lib.dto.FileTreeDto;
import com.currix.distributed_lovable.workspace_service.dto.project.FileContentResponse;
import com.currix.distributed_lovable.workspace_service.service.ProjectFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects/{projectId}/files")
@RequiredArgsConstructor
public class FileController {

    private final ProjectFileService projectFileService;

    @GetMapping
    public ResponseEntity<FileTreeDto> getFileTree(@PathVariable Long projectId) {
         return ResponseEntity.ok(projectFileService.getFileTree(projectId));
    }

    @GetMapping("/content")
    public ResponseEntity<String> getFile(
            @PathVariable Long projectId,
            @RequestParam String path
    ) {
        return ResponseEntity.ok(projectFileService.getFileContent(projectId, path));
    }

    @GetMapping("/preview/{filePath:.+}")
    public ResponseEntity<String> previewFile(
            @PathVariable Long projectId,
            @PathVariable String filePath
    ) {
        try {
            String file = projectFileService.getFileContent(projectId, filePath);

            // Determine content type based on file extension
            String contentType = getContentType(filePath);

            return ResponseEntity.ok()
                    .header("Content-Type", contentType)
                    .body(file);
        } catch (Exception e) {
            // Return a proper error page instead of letting Spring
            // return something that corrupts the iframe
            String errorHtml = """
                <!DOCTYPE html>
                <html>
                <head><title>Preview Error</title></head>
                <body style="font-family: sans-serif; display: flex; 
                             justify-content: center; align-items: center; 
                             height: 100vh; margin: 0; background: #1a1a1a; 
                             color: #ff6b6b;">
                    <div style="text-align: center;">
                        <h2>Preview Not Available</h2>
                        <p>File not found: %s</p>
                        <p style="color: #888; font-size: 14px;">
                            Click "Run Preview" after the AI generates your code.
                        </p>
                    </div>
                </body>
                </html>
                """.formatted(filePath);

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("Content-Type", "text/html")
                    .body(errorHtml);
        }
    }

    private String getContentType(String filePath) {
        if (filePath.endsWith(".html")) return "text/html; charset=utf-8";
        if (filePath.endsWith(".css")) return "text/css; charset=utf-8";
        if (filePath.endsWith(".js")) return "application/javascript; charset=utf-8";
        if (filePath.endsWith(".json")) return "application/json; charset=utf-8";
        if (filePath.endsWith(".svg")) return "image/svg+xml";
        if (filePath.endsWith(".png")) return "image/png";
        if (filePath.endsWith(".jpg") || filePath.endsWith(".jpeg")) return "image/jpeg";
        return "text/plain; charset=utf-8";
    }

}
