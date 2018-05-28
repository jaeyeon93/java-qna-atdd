package codesquad.web;

import org.junit.Test;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;

public class LoginAcceptanceTest extends AcceptanceTest {
    @Test
    public void login() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String userId = "javajigi";
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("userId", userId);
        params.add("password", "test");
        params.add("name", "자바지기");
        params.add("email", "javajigi@slipp.net");
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(params, headers);
        ResponseEntity<String> response = template().postForEntity("/users/login", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertThat(response.getHeaders().getLocation().getPath(), is("/users"));
    }
}
