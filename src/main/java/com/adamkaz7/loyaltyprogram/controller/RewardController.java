package com.adamkaz7.loyaltyprogram.controller;

import com.adamkaz7.loyaltyprogram.dto.RewardDTO;
import com.adamkaz7.loyaltyprogram.dto.RewardRequestDTO;
import com.adamkaz7.loyaltyprogram.service.RewardService;
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

@Tag(name = "Rewards", description = "Rewards management")
@RestController
@RequiredArgsConstructor
public class RewardController {
    private final RewardService rewardService;

    @Operation(summary = "Create a reward")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reward created"),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Reward with the provided name already exists",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping("/programs/{programId}/rewards")
    @ResponseStatus(HttpStatus.CREATED)
    public RewardDTO addReward(@PathVariable Long programId, @Valid @RequestBody RewardRequestDTO dto) {
        return rewardService.addReward(programId, dto);
    }

    @Operation(summary = "Get rewards by program")
    @ApiResponse(responseCode = "200", description = "Rewards list returned")
    @GetMapping("/programs/{programId}/rewards")
    public Page<RewardDTO> getRewardsByProgram(@PathVariable Long programId, Pageable pageable) {
        return rewardService.getRewardsByProgram(programId, pageable);
    }

    @Operation(summary = "Get rule by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reward found"),
            @ApiResponse(responseCode = "404", description = "Reward not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/rewards/{rewardId}")
    public RewardDTO getRewardById(@PathVariable Long rewardId) {
        return rewardService.getRewardById(rewardId);
    }

    @Operation(summary = "Update reward by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reward updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Reward not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Reward with the provided name already exists",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/rewards/{rewardId}")
    public RewardDTO updateReward(@PathVariable Long rewardId, @Valid @RequestBody RewardRequestDTO dto) {
        return rewardService.updateReward(rewardId, dto);
    }

    @Operation(summary = "Delete Reward by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reward deleted"),
            @ApiResponse(responseCode = "404", description = "Reward not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/rewards/{rewardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReward(@PathVariable Long rewardId) {
        rewardService.deleteReward(rewardId);
    }
}
