package com.jve.dto;

import java.util.List;

public record LoginResponse(String username, List<String> authorities, String token, Long especialidadId, String especialidadNombre) {
}
