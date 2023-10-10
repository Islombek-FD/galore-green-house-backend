package uz.project.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.project.common.constant.TranslationType;
import uz.project.entity.file.File;
import uz.project.entity.file.FileService;
import uz.project.entity.translation.TranslationService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/references")
public class ReferenceController {

    private final FileService fileService;
    private final TranslationService translationService;

    public ReferenceController(FileService fileService, TranslationService translationService) {
        this.fileService = fileService;
        this.translationService = translationService;
    }

    @GetMapping("/download/{uuid}")
    public ResponseEntity<Resource> download(@PathVariable UUID uuid) {
        File file = fileService.getByUuid(uuid);
        return fileService.download(file);
    }

    @GetMapping("/translations/{type}/{lang}")
    public Map<String, String> getTranslations(@PathVariable TranslationType type, @PathVariable String lang) {
        HashMap<String, String> response = new HashMap<>();
        translationService.getAllByType(type).forEach(translation -> {
            response.put(translation.getTag(), translation.getName().getByLang(lang));
        });
        return response;
    }
}
