package com.floxie.nutri_guide.infrastructure.mappers;

import org.commons.feature.user.dto.UserView;
import org.commons.feature.user_details.dto.UserDetailsView;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import com.floxie.nutri_guide.features.record.dto.RecordCreateRequest;
import com.floxie.nutri_guide.features.record.dto.RecordUpdateReqeust;
import com.floxie.nutri_guide.features.record.dto.RecordView;
import com.floxie.nutri_guide.features.record.entity.Record;

@Mapper(
    componentModel = "spring",
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
@DecoratedWith(RecordMapperDecoder.class)
public interface RecordMapper {

  Record toEntity(RecordCreateRequest dto);

  RecordView toView(Record entity);

  void update(@MappingTarget Record entity, RecordUpdateReqeust dto);
}
