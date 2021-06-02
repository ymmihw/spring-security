package com.ymmihw.spring.security.acl.persistence.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import com.ymmihw.spring.security.acl.persistence.entity.NoticeMessage;

@Repository
public interface NoticeMessageRepository extends JpaRepository<NoticeMessage, Long> {

  @Override
  @PostFilter("hasPermission(filterObject, 'READ')")
  List<NoticeMessage> findAll();

  @PostAuthorize("hasPermission(returnObject, 'READ')")
  @Query(value = "from NoticeMessage where id = ?1")
  NoticeMessage getById(Long id);

  @SuppressWarnings("unchecked")
  @Override
  @PreAuthorize("hasPermission(#noticeMessage, 'WRITE')")
  NoticeMessage save(@Param("noticeMessage") NoticeMessage noticeMessage);
}
