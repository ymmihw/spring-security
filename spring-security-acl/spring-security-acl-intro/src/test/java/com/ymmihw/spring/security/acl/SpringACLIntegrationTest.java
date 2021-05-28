package com.ymmihw.spring.security.acl;

import com.ymmihw.spring.security.acl.persistence.dao.NoticeMessageRepository;
import com.ymmihw.spring.security.acl.persistence.entity.NoticeMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SpringACLIntegrationTest {

  private static Long FIRST_MESSAGE_ID = 1L;
  private static Long SECOND_MESSAGE_ID = 2L;
  private static Long THIRD_MESSAGE_ID = 3L;
  private static String EDITTED_CONTENT = "EDITED";

  @Configuration
  @ComponentScan("com.ymmihw.spring.security.acl")
  public static class SpringConfig {}

  @Autowired private NoticeMessageRepository repo;

  @Test
  @WithMockUser(username = "manager")
  public void givenUserManager_whenFindAllMessage_thenReturnFirstMessage() {
    List<NoticeMessage> details = repo.findAll();
    assertNotNull(details);
    assertEquals(1, details.size());
    assertEquals(FIRST_MESSAGE_ID, details.get(0).getId());
  }

  @Test
  @WithMockUser(username = "manager")
  @Transactional
  @Rollback
  public void givenUserManager_whenFind1stMessageByIdAndUpdateItsContent_thenOK() {
    NoticeMessage firstMessage = repo.getById(FIRST_MESSAGE_ID);
    assertNotNull(firstMessage);
    assertEquals(FIRST_MESSAGE_ID, firstMessage.getId());

    firstMessage.setContent(EDITTED_CONTENT);
    repo.save(firstMessage);

    NoticeMessage editedFirstMessage = repo.getById(FIRST_MESSAGE_ID);
    assertNotNull(editedFirstMessage);
    assertEquals(FIRST_MESSAGE_ID, editedFirstMessage.getId());
    assertEquals(EDITTED_CONTENT, editedFirstMessage.getContent());
  }

  @Test
  @WithMockUser(username = "hr")
  public void givenUsernameHr_whenFindMessageById2_thenOK() {
    NoticeMessage secondMessage = repo.getById(SECOND_MESSAGE_ID);
    assertNotNull(secondMessage);
    assertEquals(SECOND_MESSAGE_ID, secondMessage.getId());
  }

  @Test
  @WithMockUser(username = "hr")
  public void givenUsernameHr_whenUpdateMessageWithId2_thenFail() {
    NoticeMessage secondMessage = new NoticeMessage();
    secondMessage.setId(SECOND_MESSAGE_ID);
    secondMessage.setContent(EDITTED_CONTENT);

    assertThrows(AccessDeniedException.class, () -> repo.save(secondMessage));
  }

  @Test
  @WithMockUser(roles = {"EDITOR"})
  public void givenRoleEditor_whenFindAllMessage_thenReturn3Message() {
    List<NoticeMessage> details = repo.findAll();
    assertNotNull(details);
    assertEquals(3, details.size());
  }

  @Test
  @WithMockUser(roles = {"EDITOR"})
  public void givenRoleEditor_whenUpdateThirdMessage_thenOK() {
    NoticeMessage thirdMessage = new NoticeMessage();
    thirdMessage.setId(THIRD_MESSAGE_ID);
    thirdMessage.setContent(EDITTED_CONTENT);
    repo.save(thirdMessage);
  }

  @Test
  @WithMockUser(roles = {"EDITOR"})
  @Transactional
  @Rollback
  public void givenRoleEditor_whenFind1stMessageByIdAndUpdateContent_thenFail() {
    NoticeMessage firstMessage = repo.getById(FIRST_MESSAGE_ID);
    assertNotNull(firstMessage);
    assertEquals(FIRST_MESSAGE_ID, firstMessage.getId());
    firstMessage.setContent(EDITTED_CONTENT);

    assertThrows(AccessDeniedException.class, () -> repo.save(firstMessage));
  }
}
