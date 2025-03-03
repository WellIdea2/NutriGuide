package com.floxie.nutri_guide.infrastructure.config.openfeign;

import org.commons.feature.user_details.dto.UserDetailsView;
import org.commons.feature.user_details.paths.UserDetailsControllerPaths;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "user-details", configuration = AuthorizationInterceptorConfig.class)
public interface UserDetailsClient {

  @GetMapping(
      value = UserDetailsControllerPaths.BASE + UserDetailsControllerPaths.ME,
      produces = MediaType.APPLICATION_JSON_VALUE)
  UserDetailsView getUserDetails();
}
