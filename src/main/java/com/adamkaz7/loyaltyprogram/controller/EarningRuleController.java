package com.adamkaz7.loyaltyprogram.controller;

import com.adamkaz7.loyaltyprogram.dto.EarningRuleDTO;
import com.adamkaz7.loyaltyprogram.dto.EarningRuleRequestDTO;
import com.adamkaz7.loyaltyprogram.service.EarningRuleService;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Earning rules", description = "Earning rules management")
@RestController
@RequiredArgsConstructor
public class EarningRuleController {
    private final EarningRuleService earningRuleService;

    @Operation(summary = "Create a earning rule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rule created"),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Rule with the provided name already exists",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping("/programs/{programId}/earning-rules")
    @ResponseStatus(HttpStatus.CREATED)
    public EarningRuleDTO addRule(@PathVariable Long programId, @Valid @RequestBody EarningRuleRequestDTO dto) {
        return earningRuleService.addRule(programId, dto);
    }

    @Operation(summary = "Get rules by program")
    @ApiResponse(responseCode = "200", description = "Rules list returned")
    @GetMapping("/programs/{programId}/earning-rules")
    public Page<EarningRuleDTO> getRulesByProgram(@PathVariable Long programId, Pageable pageable) {
        return earningRuleService.getRulesByProgram(programId, pageable);
    }

    @Operation(summary = "Get rule by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rule found"),
            @ApiResponse(responseCode = "404", description = "Rule not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/earning-rules/{ruleId}")
    public EarningRuleDTO getRuleById(@PathVariable Long ruleId) {
        return earningRuleService.getRuleById(ruleId);
    }

    @Operation(summary = "Update rule by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rule updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Rule not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Rule with the provided name already exists",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/earning-rules/{ruleId}")
    public EarningRuleDTO updateRule(@PathVariable Long ruleId, @Valid @RequestBody EarningRuleRequestDTO dto) {
        return earningRuleService.updateRule(ruleId, dto);
    }

    @Operation(summary = "Delete rule by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Rule deleted"),
            @ApiResponse(responseCode = "404", description = "Rule not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/earning-rules/{ruleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRule(@PathVariable Long ruleId) {
        earningRuleService.deleteRule(ruleId);
    }
}
