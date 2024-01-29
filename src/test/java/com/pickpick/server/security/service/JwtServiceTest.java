package com.pickpick.server.security.service;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pickpick.server.domain.Users;
import com.pickpick.server.domain.enums.PublicStatus;
import com.pickpick.server.domain.enums.ShareStatus;
import com.pickpick.server.dto.AddUserRequestDto;
import com.pickpick.server.repository.UsersRepository;
import com.pickpick.server.service.UsersService;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class JwtServiceTest {


	@Autowired
	JwtService jwtService;
	@Autowired
	UsersRepository usersRepository;
	@Autowired
	UsersService usersService;
	@Autowired
	EntityManager em;

	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.access.header}")
	private String accessHeader;
	@Value("${jwt.refresh.header}")
	private String refreshHeader;

	private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
	private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
	private static final String USERNAME_CLAIM = "email";
	private static final String BEARER = "Bearer ";


	private String email = "dh1010a@naver.com";

	@BeforeEach
	public void init() {
		usersService.save(AddUserRequestDto.builder().name("도현").email(email).password("1234").phoneNum("01054888")
				.publicStatus(PublicStatus.PUBLIC).shareStatus(ShareStatus.SHAREABLE).build());
		clear();
	}

	@Test
	public void accessToken_발급_성공() throws Exception {
		//given, when
		String accessToken = jwtService.createAccessToken(email);
		DecodedJWT verify = getVerify(accessToken);
		String subject = verify.getSubject();
		String findEmail = verify.getClaim(USERNAME_CLAIM).asString();

		//then
		assertThat(findEmail).isEqualTo(email);
		assertThat(subject).isEqualTo(ACCESS_TOKEN_SUBJECT);
	}

	@Test
	public void refreshToken_발급_성공() throws Exception{
	    //given, when
		String refreshToken = jwtService.createRefreshToken();
		DecodedJWT verify = getVerify(refreshToken);
		String subject = verify.getSubject();
		String findEmail = verify.getClaim(USERNAME_CLAIM).asString();

		//then
		assertThat(subject).isEqualTo(REFRESH_TOKEN_SUBJECT);
		assertThat(findEmail).isNull();
	}

	@Test
	public void updateRefreshToken_성공() throws Exception {
		//given
		String refreshToken = jwtService.createRefreshToken();
		jwtService.updateRefreshToken(email, refreshToken);
		clear();
		Thread.sleep(3000);

		//when
		String reIssuedRefreshToken = jwtService.createRefreshToken();
		jwtService.updateRefreshToken(email, reIssuedRefreshToken);
		clear();

		//then
		assertThrows(Exception.class, () -> usersRepository.findByRefreshToken(refreshToken).get());//
		assertThat(usersRepository.findByRefreshToken(reIssuedRefreshToken).get().getEmail()).isEqualTo(email);
	}

	@Test
	public void destroyRefreshToken_refreshToken_제거_성공() throws Exception {
		//given
		String refreshToken = jwtService.createRefreshToken();
		jwtService.updateRefreshToken(email, refreshToken);
		clear();

		//when
		jwtService.destroyRefreshToken(email);
		clear();

		//then
		assertThrows(Exception.class, () -> usersRepository.findByRefreshToken(refreshToken).get());

		Users users = usersRepository.findByEmail(email).get();
		assertThat(users.getRefreshToken()).isNull();
	}

	@Test
	public void setAccessTokenHeader_헤더_설정_성공() throws Exception {
		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

		String accessToken = jwtService.createAccessToken(email);
		String refreshToken = jwtService.createRefreshToken();

		jwtService.setAccessTokenHeader(mockHttpServletResponse, accessToken);

		//when
		jwtService.sendAccessAndRefreshToken(mockHttpServletResponse,accessToken,refreshToken);

		//then
		String headerAccessToken = mockHttpServletResponse.getHeader(accessHeader);

		assertThat(headerAccessToken).isEqualTo(accessToken);
	}



	@Test
	public void setRefreshTokenHeader_헤더_설정_성공() throws Exception {
		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

		String accessToken = jwtService.createAccessToken(email);
		String refreshToken = jwtService.createRefreshToken();

		jwtService.setRefreshTokenHeader(mockHttpServletResponse, refreshToken);


		//when
		jwtService.sendAccessAndRefreshToken(mockHttpServletResponse,accessToken,refreshToken);

		//then
		String headerRefreshToken = mockHttpServletResponse.getHeader(refreshHeader);

		assertThat(headerRefreshToken).isEqualTo(refreshToken);
	}

	@Test
	public void sendAccessAndRefreshToken_토큰_전송_성공() throws Exception {
		//given
		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

		String accessToken = jwtService.createAccessToken(email);
		String refreshToken = jwtService.createRefreshToken();

		//when
		jwtService.sendAccessAndRefreshToken(mockHttpServletResponse,accessToken,refreshToken);

		//then
		String headerAccessToken = mockHttpServletResponse.getHeader(accessHeader);
		String headerRefreshToken = mockHttpServletResponse.getHeader(refreshHeader);

		assertThat(headerAccessToken).isEqualTo(accessToken);
		assertThat(headerRefreshToken).isEqualTo(refreshToken);

	}

	@Test
	public void extractAccessToken_추출_성공() throws Exception {
		//given
		String accessToken = jwtService.createAccessToken(email);
		String refreshToken = jwtService.createRefreshToken();
		HttpServletRequest httpServletRequest = setRequest(accessToken, refreshToken);

		//when
		String extractAccessToken = jwtService.extractAccessToken(httpServletRequest).orElseThrow(()-> new Exception("토큰이 없습니다"));

		//then
		assertThat(extractAccessToken).isEqualTo(accessToken);
		assertThat(getVerify(extractAccessToken).getClaim(USERNAME_CLAIM).asString()).isEqualTo(email);
	}

	@Test
	public void extractRefreshToken_추출_성공() throws Exception {
		//given
		String accessToken = jwtService.createAccessToken(email);
		String refreshToken = jwtService.createRefreshToken();
		HttpServletRequest httpServletRequest = setRequest(accessToken, refreshToken);

		//when
		String extractRefreshToken = jwtService.extractRefreshToken(httpServletRequest).orElseThrow(()-> new Exception("토큰이 없습니다"));

		//then
		assertThat(extractRefreshToken).isEqualTo(refreshToken);
		assertThat(getVerify(extractRefreshToken).getSubject()).isEqualTo(REFRESH_TOKEN_SUBJECT);
	}

	@Test
	public void extractEmail_추출_성공() throws Exception {
		//given
		String accessToken = jwtService.createAccessToken(email);
		String refreshToken = jwtService.createRefreshToken();
		HttpServletRequest httpServletRequest = setRequest(accessToken, refreshToken);

		String requestAccessToken = jwtService.extractAccessToken(httpServletRequest).orElseThrow(()->new Exception("토큰이 없습니다"));

		//when
		String extractEmail = jwtService.extractEmail(requestAccessToken).orElseThrow(()-> new Exception("유저 조회에 실패하였습니다"));

		//then
		assertThat(extractEmail).isEqualTo(email);
	}

	@Test
	public void 토큰_유효성_검사() throws Exception {
		//given
		String accessToken = jwtService.createAccessToken(email);
		String refreshToken = jwtService.createRefreshToken();

		//when, then
		assertThat(jwtService.isTokenValid(accessToken)).isTrue();
		assertThat(jwtService.isTokenValid(refreshToken)).isTrue();
		assertThat(jwtService.isTokenValid(accessToken+"d")).isFalse();
		assertThat(jwtService.isTokenValid(accessToken+"d")).isFalse();

	}

	private HttpServletRequest setRequest(String accessToken, String refreshToken) throws IOException {

		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		jwtService.sendAccessAndRefreshToken(mockHttpServletResponse,accessToken,refreshToken);

		String headerAccessToken = mockHttpServletResponse.getHeader(accessHeader);
		String headerRefreshToken = mockHttpServletResponse.getHeader(refreshHeader);

		MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();

		httpServletRequest.addHeader(accessHeader, BEARER+headerAccessToken);
		httpServletRequest.addHeader(refreshHeader, BEARER+headerRefreshToken);

		return httpServletRequest;
	}

	private void clear() {
		em.flush();
		em.clear();
	}

	private DecodedJWT getVerify(String token) {
		return JWT.require(HMAC512(secret)).build().verify(token);
	}
}