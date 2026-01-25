package pl.mikbac.examples.example104.dto;

import java.util.UUID;

/**
 * Created by MikBac on 22.01.2026
 */

public record UploadResponse(UUID confirmationId,
                             Long productsCount) {
}
