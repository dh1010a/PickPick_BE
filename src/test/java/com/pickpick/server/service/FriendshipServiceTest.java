package com.pickpick.server.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pickpick.server.domain.Users;
import com.pickpick.server.domain.enums.PublicStatus;
import com.pickpick.server.domain.enums.ShareStatus;
import com.pickpick.server.dto.UserSignupDto;
import com.pickpick.server.repository.UsersRepository;
import jakarta.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class FriendshipServiceTest {

	@Autowired
	UsersService usersService;

	@Autowired
	EntityManager em;

	@Autowired
	FriendshipService friendshipService;

	@Autowired
	UsersRepository usersRepository;

	@Autowired
	MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();

	private static String FROM_EMAIL = "dh1010a@naver.com";
	private static String TO_EMAIL = "dh1010a2@naver.com";

	private static String KEY_USERNAME = "email";
	private static String KEY_PASSWORD = "password";
	private static String USERNAME = "dh1010a@naver.com";
	private static String PASSWORD = "1234";

	private static String LOGIN_RUL = "/login";

	private static String REQUEST_URL = "/users/request/dh1010a2@naver.com";

	@BeforeEach
	private void init() {
		usersService.save(UserSignupDto.builder().name("도현").email("dh1010a@naver.com").password("1234").phoneNum("01054888")
				.publicStatus(PublicStatus.PUBLIC).shareStatus(ShareStatus.SHAREABLE).build());
		usersService.save(UserSignupDto.builder().name("도현2").email("dh1010a2@naver.com").password("1234").phoneNum("01054888")
				.publicStatus(PublicStatus.PUBLIC).shareStatus(ShareStatus.SHAREABLE).build());
		clear();
	}

	private Map getUsernamePasswordMap(String username, String password) {
		Map<String, String> map = new HashMap<>();
		map.put(KEY_USERNAME, username);
		map.put(KEY_PASSWORD, password);
		return map;
	}


	private ResultActions perform(String url, MediaType mediaType, Map usernamePasswordMap) throws Exception {
		return mockMvc.perform(MockMvcRequestBuilders
				.post(url)
				.contentType(mediaType)
				.content(objectMapper.writeValueAsString(usernamePasswordMap)));

	}

	private void clear() {
		em.flush();
		em.clear();
	}

	@Test
	public void 친구요청_생성_성공() throws Exception{
	    //given
		Users fromUser = usersRepository.findByEmail(FROM_EMAIL).orElseThrow(() -> new Exception("회원 조회 실패"));
		Users toUser = usersRepository.findByEmail(TO_EMAIL).orElseThrow(() -> new Exception("회원 조회 실패"));
		Map<String, String> map = getUsernamePasswordMap(USERNAME, PASSWORD);
		//when
//	    friendshipService.createFriendship();
	    //then
		MvcResult result = perform(LOGIN_RUL, APPLICATION_JSON, map)
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}

}