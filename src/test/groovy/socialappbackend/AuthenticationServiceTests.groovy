import com.pw.socialappbackend.model.User
import com.pw.socialappbackend.service.AuthenticationService
import com.pw.socialappbackend.service.impl.AuthenticationServiceImpl
import spock.lang.Specification

import javax.sql.DataSource

class AuthenticationServiceSpec extends Specification {

    def "should generate token"() {

        given:
            AuthenticationService authService = new AuthenticationServiceImpl()
        when:
            String generatedToken = authService.generateToken()
        then:
            128 == generatedToken.length()
    }

    def "should not generate same token"() {

        given:
            AuthenticationService authService = new AuthenticationServiceImpl()
        when:
            String generatedTokenOne = authService.generateToken()
            String generatedTokenTwo = authService.generateToken()
        then:
            !generatedTokenOne.equals(generatedTokenTwo)
    }

    def "should prepare response"() {

        given:
            String username = "Bob"
            AuthenticationService authService = new AuthenticationServiceImpl()
        when:
            User response = authService.prepareResponse(username)
        then:
            username.equals(response.getUsername())
            !response.getToken().isEmpty()
    }

    def "should authenticate user"() {

        given:
            User userToAuthenticate = new User("Bob", "secret")
            AuthenticationService authService = new AuthenticationServiceImpl()
        when:
            boolean isAuthenticated = authService.authenticateUser(userToAuthenticate)
        then:
            isAuthenticated
    }
}