package com.floxie.nutri_guide.web;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.commons.feature.nutri_guide.paths.RecordControllerPaths;
import com.floxie.nutri_guide.features.record.dto.RecordCreateRequest;
import com.floxie.nutri_guide.features.record.dto.RecordUpdateReqeust;
import com.floxie.nutri_guide.features.record.dto.RecordView;
import com.floxie.nutri_guide.features.record.services.RecordService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RecordControllerPaths.BASE)
@RequiredArgsConstructor
public class RecordController {

  private final RecordService recordService;

  @GetMapping(RecordControllerPaths.GET_ALL)
  public ResponseEntity<Page<RecordView>> getAll(Pageable pageable) {
    return new ResponseEntity<>(recordService.getAll(pageable), HttpStatus.OK);
  }

  @GetMapping(RecordControllerPaths.GET_BY_ID)
  @PreAuthorize("@recordEvaluator.isOwner(#id) || hasRole('ADMIN')")
  public ResponseEntity<RecordView> getById(@PathVariable UUID id) {
    return new ResponseEntity<>(recordService.getById(id), HttpStatus.OK);
  }

  @PostMapping(RecordControllerPaths.CREATE)
  public ResponseEntity<RecordView> create(@RequestBody @Valid RecordCreateRequest dto) {

    return ResponseEntity.ok(recordService.create(dto));
  }

  @PatchMapping(RecordControllerPaths.UPDATE)
  @PreAuthorize("@recordEvaluator.isOwner(#id) || hasRole('ADMIN')")
  public ResponseEntity<RecordView> update(@PathVariable UUID id,
      @RequestBody @Valid RecordUpdateReqeust dto) {
    return ResponseEntity.ok(recordService.update(id, dto));
  }

  @DeleteMapping(RecordControllerPaths.DELETE)
  @PreAuthorize("@recordEvaluator.isOwner(#id) || hasRole('ADMIN')")
  public ResponseEntity<HttpStatus> delete(@PathVariable UUID id) {
    recordService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}