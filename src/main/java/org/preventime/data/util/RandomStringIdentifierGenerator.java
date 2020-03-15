package org.preventime.data.util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.security.SecureRandom;

public class RandomStringIdentifierGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o)
            throws HibernateException {

        return randomStringAlphaNumeric(16);

    }

    public static String randomStringAlphaNumeric(int size) {
        return randomString(AB, size);
    }

    public static String randomString(String candidates, int size) {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder sb = new StringBuilder(size);
        for( int i = 0; i < size; i++ )
            sb.append( candidates.charAt( secureRandom.nextInt(candidates.length()) ) );
        return sb.toString();
    }

    private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";


}
