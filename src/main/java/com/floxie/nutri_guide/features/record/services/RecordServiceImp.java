package com.floxie.nutri_guide.features.record.services;

import static com.floxie.nutri_guide.infrastructure.exception.ExceptionMessages.RECORD_NOT_FOUND;

import com.floxie.nutri_guide.features.record.dto.RecordCreateRequest;
import com.floxie.nutri_guide.features.record.dto.RecordUpdateReqeust;
import com.floxie.nutri_guide.features.record.dto.RecordView;
import com.floxie.nutri_guide.features.record.entity.Record;
import com.floxie.nutri_guide.features.record.entity.RecordDetails;
import com.floxie.nutri_guide.features.record.repository.RecordRepository;
import com.floxie.nutri_guide.features.record.repository.RecordSpecification;
import com.floxie.nutri_guide.infrastructure.config.openfeign.UserDetailsClient;
import com.floxie.nutri_guide.infrastructure.config.security.SecurityUtils;
import com.floxie.nutri_guide.infrastructure.mappers.RecordMapper;
import java.time.LocalDate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.commons.exceptions.throwable.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecordServiceImp implements RecordService {

  private final RecordMapper recordMapper;
  private final RecordRepository repository;
  private final UserDetailsClient userDetailsClient;

  public Page<RecordView> getAll(Pageable pageable) {
    var user = SecurityUtils.getCurrentLoggedInUser();

    return repository.findAll(new RecordSpecification(user), pageable).map(recordMapper::toView);
  }

  public RecordView getById(UUID recordId) {
    return recordMapper.toView(findById(recordId));
  }

  public RecordView create(RecordCreateRequest dto) {
    var loggedInUser = SecurityUtils.getCurrentLoggedInUser();
    var date = dto.date() != null ? dto.date() : LocalDate.now();

    if (repository.existsByDateAndUserId(date, loggedInUser.id())) {
      return recordMapper.toView(repository.findByDateAndUserId(date, loggedInUser.id()));
    }

    var record = recordMapper.toEntity(dto);
    var userDetails = userDetailsClient.getUserDetails();
    var recordDetails = new RecordDetails(userDetails);
    recordDetails.setRecord(record);

    record.setUserId(loggedInUser.id());
    record.setRecordDetails(recordDetails);
    record.setDate(date);
    record.setDailyCalories(record.getCaloriesPerDay());

    return recordMapper.toView(repository.save(record));
  }

  public RecordView update(UUID id, RecordUpdateReqeust dto) {
    var record = findById(id);

    recordMapper.update(record, dto);

    return recordMapper.toView(repository.save(record));
  }

  @Transactional
  public void delete(UUID recordId) {
    if (!repository.existsById(recordId)) {
      throw new NotFoundException(RECORD_NOT_FOUND, recordId);
    }

    repository.deleteById(recordId);
  }

  public Record findByIdAndUserId(UUID recordId, UUID userId) {
    return repository
        .findByIdAndUserId(recordId, userId)
        .orElseThrow(() -> new NotFoundException(RECORD_NOT_FOUND, recordId));
  }

  public Record findById(UUID recordId) {
    return repository
        .findById(recordId)
        .orElseThrow(() -> new NotFoundException(RECORD_NOT_FOUND, recordId));
  }

  @Transactional
  public void deleteAllByUserId(UUID userId) {
    repository.deleteAllByUserId(userId);
  }

  public Record save(Record record) {
    return repository.save(record);
  }

  public boolean isOwner(UUID recordId, UUID userId) {
    return repository.existsByIdAndUserId(recordId, userId);
  }
}
