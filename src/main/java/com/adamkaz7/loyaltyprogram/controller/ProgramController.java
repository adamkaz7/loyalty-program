package com.adamkaz7.loyaltyprogram.controller;

import com.adamkaz7.loyaltyprogram.dto.ProgramDTO;
import com.adamkaz7.loyaltyprogram.service.ProgramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Programs", description = "Loyalty programs")
@RestController
@RequestMapping("/programs")
@RequiredArgsConstructor
public class ProgramController {
    private final ProgramService programService;

    @Operation(summary = "Create a program")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Program created"),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Program with the provided name already exists",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProgramDTO addProgram(@Valid @RequestBody ProgramDTO dto) {
        return programService.addProgram(dto);
    }

    @Operation(summary = "Get all programs")
    @ApiResponse(responseCode = "200", description = "Program list returned")
    @GetMapping
    public Page<ProgramDTO> getAllPrograms(boolean active, Pageable pageable) {
        return programService.getAllPrograms(active, pageable);
    }

    @Operation(summary = "Get program by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Program found"),
            @ApiResponse(responseCode = "404", description = "Program not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{programId}")
    public ProgramDTO getProgramById(@PathVariable Long programId) {
        return programService.getProgramById(programId);
    }

    @Operation(summary = "Update program by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Program updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Program not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Program with the provided name already exists",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/{programId}")
    public ProgramDTO updateProgram(@PathVariable Long programId, @Valid @RequestBody ProgramDTO dto) {
        return programService.updateProgram(programId, dto);
    }

    @Operation(summary = "Delete program by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Program deleted"),
            @ApiResponse(responseCode = "404", description = "Program not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/{programId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProgram(@PathVariable Long programId) {
        programService.deleteProgram(programId);
    }
}
