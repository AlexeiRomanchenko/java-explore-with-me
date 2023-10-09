package ru.practicum.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.CompilationService;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequestDto;
import ru.practicum.discriptions.MessageManager;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class CompilationAdminController {
    private final CompilationService compilationService;

    @PostMapping("/admin/compilations")
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createCompilation(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        log.info(MessageManager.receivedPost, "/admin/compilations");
        return compilationService.createCompilation(newCompilationDto);
    }

    @PatchMapping("/admin/compilations/{compId}")
    public CompilationDto updateCompilation(@PathVariable @Positive Long compId,
                                            @RequestBody @Valid
                                            UpdateCompilationRequestDto updateCompilationRequestDto) {
        log.info(MessageManager.receivedPatch, "/admin/compilations", compId);
        return compilationService.updateCompilation(compId, updateCompilationRequestDto);
    }

    @DeleteMapping(value = "/admin/compilations/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable @Positive Long compId) {
        log.info(MessageManager.receivedDelete, "/admin/compilations", compId);
        compilationService.deleteCompilation(compId);
    }
}