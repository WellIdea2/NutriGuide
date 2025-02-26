package com.floxie.nutri_guide.features.record.services;

import java.util.UUID;
import com.floxie.nutri_guide.features.record.dto.RecordCreateRequest;
import com.floxie.nutri_guide.features.record.dto.RecordUpdateReqeust;
import com.floxie.nutri_guide.features.record.dto.RecordView;
import com.floxie.nutri_guide.features.record.entity.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecordService {

  Page<RecordView> getAll(Pageable pageable);

  RecordView getById(UUID recordId);

  RecordView create(RecordCreateRequest dto);

  RecordView update(UUID id, RecordUpdateReqeust dto);

  void delete(UUID recordId);

  Record findByIdAndUserId(UUID recordId, UUID userId);

  void deleteAllByUserId(UUID userId);

  Record save(Record record);

  boolean isOwner(UUID recordId, UUID userId);
}
