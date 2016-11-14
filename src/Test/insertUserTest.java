package Test;

import org.junit.Before;
import org.junit.Test;

import model.AuthorizedUser;
import model.DBInterfacer;
import model.Profile;
import model.Worker;

import static org.junit.Assert.*;


/**
 *
 * @author Geetika Kapoor
 *
 */
public class insertUserTest {

    private AuthorizedUser testUser;
    private AuthorizedUser setUpUser;

    private static final String DEF_USERNAME = "Superman";
    private static final String DEF_PASSWORD = "DefinitelyNotClark";
    private static final String DEF_NAME = "George Burdell";
    private static final String DEF_EMAIL = "burdell@gatech.edu";
    private static final String DEF_TITLE = "Mr.";
    private static final String DEF_HOME = "GaTech";
    private static final String DEF_USERTYPE = "Worker";

    @Before
    public void setUp() {
        setUpUser = new Worker(DEF_USERNAME, DEF_PASSWORD, new Profile(DEF_NAME, DEF_EMAIL, DEF_TITLE, DEF_HOME));
        DBInterfacer.deleteUser(DEF_USERNAME);
    }

    @Test
    public void inserTest() {
        assertTrue(DBInterfacer.insertUser(DEF_USERNAME, DEF_PASSWORD, DEF_USERTYPE));
        assertTrue(DBInterfacer.updateProfile(DEF_NAME, DEF_EMAIL, DEF_TITLE, DEF_HOME, DEF_USERNAME));

        testUser = DBInterfacer.getUser(DEF_USERNAME, DEF_PASSWORD);

        equalUsers(setUpUser, testUser);

        assertFalse(DBInterfacer.insertUser(testUser.getUsername(), testUser.getPassword(), DEF_USERTYPE));
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
