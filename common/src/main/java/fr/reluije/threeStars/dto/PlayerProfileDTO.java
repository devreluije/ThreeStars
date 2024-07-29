package fr.reluije.threeStars.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record PlayerProfileDTO(Long id, UUID uniqueId, String name) {}
