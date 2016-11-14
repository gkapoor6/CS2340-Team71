package Test;

import org.junit.Before;
import org.junit.Test;

import model.AuthorizedUser;
import model.Profile;
import model.User;
import model.DBInterfacer;

import static org.junit.Assert.*;

/**
 *
 * @author Swati Mardia
 *
 */
public class updateProfileTest {

    private AuthorizedUser testUser;

    private AuthorizedUser setUpUser;
    private Profile setUpProfile;
    private static final String DEF_USERNAME = "Batman";
    private static final String DEF_PASSWORD = "HARAMBE";
    private static final String DEF_NAME = "Bob Waters";
    private static final String DEF_EMAIL = "bob@gatech.edu";
    private static final String DEF_TITLE = "Mr.";
    private static final String DEF_HOME = "GaTech";
    private static final String DEF_USERTYPE = "User";

    private static final String NEW_NAME = "Clough";
    private static final String NEW_EMAIL = "Klaus@gatech.edu";
    private static final String NEW_TITLE = "Mrs.";
    private static final String NEW_HOME = "Howey";



    @Before
    public void setUp() {
        setUpProfile = new Profile(DEF_NAME, DEF_EMAIL, DEF_TITLE, DEF_HOME);
        setUpUser = new User(DEF_USERNAME, DEF_PASSWORD, setUpProfile);
        DBInterfacer.deleteUser(setUpUser.getUsername());
    }

    @Test
    public void testUpdate() {
        DBInterfacer.insertUser(DEF_USERNAME, DEF_PASSWORD, DEF_USERTYPE);
        DBInterfacer.updateProfile(DEF_NAME, DEF_EMAIL, DEF_TITLE, DEF_HOME, DEF_USERNAME);
        testUser = DBInterfacer.getUser(DEF_USERNAME, DEF_PASSWORD);

        equalProfiles(testUser.getProfile(), setUpProfile);

        assertTrue("Could not update",
                DBInterfacer.updateProfile(NEW_NAME, NEW_EMAIL, NEW_TITLE, NEW_HOME, DEF_USERNAME));
        testUser = DBInterfacer.getUser(DEF_USERNAME, DEF_PASSWORD);

        differentProfiles(testUser.getProfile(), setUpProfile);

        setUpProfile.setName(NEW_NAME);
        setUpProfile.setEmail(NEW_EMAIL);
        setUpProfile.setTitle(NEW_TITLE);
        setUpProfile.setHome(NEW_HOME);

        equalProfiles(testUser.getProfile(), setUpProfile);

    }

    private void differentProfiles(Profile first, Profile second) {
        assertNotEquals("Wrong name", first.getNameProperty().get(), second.getNameProperty().get());
        assertNotEquals("Wrong email", first.getEmailProperty().get(), second.getEmailProperty().get());
        assertNotEquals("Wrong title", first.getTitleProperty().get(), second.getTitleProperty().get());
        assertNotEquals("Wrong name", first.getHomeProperty().get(), second.getHomeProperty().get());
    }

    private void equalProfiles(Profile first, Profile second) {
        assertEquals("Wrong name", first.getNameProperty().get(), second.getNameProperty().get());
        assertEquals("Wrong email", first.getEmailProperty().get(), second.getEmailProperty().get());
        assertEquals("Wrong title", first.getTitleProperty().get(), second.getTitleProperty().get());
        assertEquals("Wrong name", first.getHomeProperty().get(), second.getHomeProperty().get());
    }
}
