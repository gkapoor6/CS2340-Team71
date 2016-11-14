package Test;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import model.AuthorizedUser;
import model.DBInterfacer;
import model.Profile;
import model.Manager;

/**
 *
 * @author Ke Hairong
 *
 */
public class getUserTest {

    private AuthorizedUser setUpUser;
    private AuthorizedUser testUser;

    private static final String DEF_USERNAME = "Superman";
    private static final String DEF_PASSWORD = "DefinitelyNotClark";
    private static final String DEF_NAME = "George Burdell";
    private static final String DEF_EMAIL = "burdell@gatech.edu";
    private static final String DEF_TITLE = "Mr.";
    private static final String DEF_HOME = "GaTech";
    private static final String DEF_USERTYPE = "Manager";

    private static final String WRONG_USERNAME = "I AM WRONG";
    private static final String WRONG_PASSWORD = "GOOD PASSWORD";

    @Before
    public void setUp() {
        setUpUser = new Manager(DEF_USERNAME, DEF_PASSWORD, new Profile(DEF_NAME, DEF_EMAIL, DEF_TITLE, DEF_HOME));
        DBInterfacer.deleteUser(DEF_USERNAME);
    }

    @Test
    public void getTest() {
        assertTrue(DBInterfacer.insertUser(DEF_USERNAME, DEF_PASSWORD, DEF_USERTYPE));
        assertTrue(DBInterfacer.updateProfile(DEF_NAME, DEF_EMAIL, DEF_TITLE, DEF_HOME, DEF_USERNAME));

        assertEquals("Both pass and username should be wrong",
                DBInterfacer.getUser(WRONG_USERNAME, WRONG_PASSWORD), null);
        assertEquals("Username should be wrong", DBInterfacer.getUser(WRONG_USERNAME, DEF_PASSWORD), null);
        assertEquals("Password should be wrong", DBInterfacer.getUser(DEF_USERNAME, WRONG_PASSWORD), null);

        testUser = DBInterfacer.getUser(setUpUser.getUsername(), setUpUser.getPassword());
        equalUsers(testUser, setUpUser);
    }

    private void equalUsers(AuthorizedUser first, AuthorizedUser second) {
        assertEquals("Wrong name", first.getProfile().getNameProperty().get(), second.getProfile().getNameProperty().get());
        assertEquals("Wrong email", first.getProfile().getEmailProperty().get(), second.getProfile().getEmailProperty().get());
        assertEquals("Wrong title", first.getProfile().getTitleProperty().get(), second.getProfile().getTitleProperty().get());
        assertEquals("Wrong name", first.getProfile().getHomeProperty().get(), second.getProfile().getHomeProperty().get());
        assertEquals("Wrong username", first.getUsername(), second.getUsername());
        assertEquals("Wrong password", first.getPassword(), second.getPassword());
        assertEquals("Wrong user type", first.getClass().getSimpleName(), second.getClass().getSimpleName());
    }
}
