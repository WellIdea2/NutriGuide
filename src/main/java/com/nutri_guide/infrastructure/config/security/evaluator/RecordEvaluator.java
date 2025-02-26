package com.nutri_guide.infrastructure.config.security.evaluator;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.commons.feature.user.dto.UserView;

import com.nutri_guide.features.record.services.RecordService;
import com.nutri_guide.infrastructure.config.security.SecurityUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecordEvaluator {

  private final RecordService service;

  public boolean isOwner(UUID recordId) {
    UserView user = SecurityUtils.getCurrentLoggedInUser();

    return service.isOwner(recordId, user.id());
  }
}
