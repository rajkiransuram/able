package able.user;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.wideplay.warp.persist.Transactional;
import org.hibernate.Session;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Singleton
public class UserManager {
    private Blowfish cipher;
    private Provider<Session> session;
    private UserAccess userAccess;

    @Inject
    public UserManager(Provider<Session> session, UserAccess userAccess, Blowfish cipher) {
        this.session = session;
        this.userAccess = userAccess;
        this.cipher = cipher;
    }

    @Transactional
    public void create(User user) {
        // encrypt the password one-way
        user.setPassword(encrypt(user.getPassword()));

        session.get().save(user);
    }

    public boolean authenticate(String email, String password) {
        String encrypted = encrypt(password);
        User user = userAccess.findByEmailAndPassword(email, encrypted);
        return user != null;
    }

    @Transactional
    public User findById(long userId) {
        return userAccess.findById(userId);
    }

    private synchronized String encrypt(String plainText) {
        MessageDigest md;

        try {
            md = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmException e) {
            String msg = "SHA not found, encryption cannot continue, no recovery possible";
            throw new RuntimeException(msg, e);
        }

        try {
            md.update(plainText.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            String msg = "UTF-8 encoding not found, no recovery possible";
            throw new RuntimeException(msg, e);
        }

        byte[] raw = md.digest();

        return new BASE64Encoder().encode(raw);
    }

    public String encryptAuthInfo(String email, String password) {
        if (email == null || password == null) {
            throw new NullPointerException("Email or password was null.");
        }
        return cipher.encryptString(email + '\002' + password);
    }

    public String[] decryptAuthInfo(String value) {
        // Check that the cookie value isn't null or zero-length
        if (value == null || value.length() <= 0) {
            return null;
        }
        // Decode the cookie value
        value = cipher.decryptString(value);
        if (value == null) {
            return null;
        }

        int pos = value.indexOf('\002');
        String email = (pos < 0) ? "" : value.substring(0, pos);
        String password = (pos < 0) ? "" : value.substring(pos + 1);

        return new String[]{email, password};
    }

    public User findByEmail(String email) {
        return userAccess.findByEmail(email);
    }

    @Transactional
    public void update(User user) {
        session.get().update(user);
    }
}
