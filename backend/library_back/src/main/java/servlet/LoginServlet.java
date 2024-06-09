package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.LoginService;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.util.Objects;

public class LoginServlet extends HttpServlet {

    final String MODE_LIBRARIAN = "0";
    final String MODE_PATRON = "1";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            ObjectMapper objectMapper = new ObjectMapper();
            UserRequest userRequest = objectMapper.readValue(request.getReader(), UserRequest.class);
            LoginService loginService = new LoginService();
            Object user = null;

            if (Objects.equals(userRequest.mode, MODE_LIBRARIAN)) {
                user = loginService.loginLibrarian(userRequest.id, userRequest.password);
            } else if (Objects.equals(userRequest.mode, MODE_PATron)) {
                user = loginService.loginPatron(userRequest.id, userRequest.password);
            } else {
                throw new IllegalArgumentException("Invalid mode: " + userRequest.mode);
            }

            String json;
            if (user == null) {
                json = objectMapper.writeValueAsString("Login failed");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                json = objectMapper.writeValueAsString(user);
            }

            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
        } catch (Exception e) {
            response.sendError(HttpURLConnection.HTTP_INTERNAL_ERROR, "Server error");
        }
    }

    private static class UserRequest {
        public String mode;
        public String id;
        public String password;
    }
}
