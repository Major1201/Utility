package com.major.util.mail;

import org.apache.commons.collections4.CollectionUtils;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.internet.InternetAddress;
import javax.mail.search.AndTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.FromTerm;
import javax.mail.search.SearchTerm;
import java.util.List;

/**
 * User: Minjie
 * Date: 13-10-29
 * Time: 下午10:38
 */
@SuppressWarnings("UnusedDeclaration")
public class SearchCondition {
    /**
     * Search all mails.
     */
    public static final SearchTerm ALL = null;

    /**
     * Search all unseen mails.
     */
    public static final SearchTerm UNSEEN = new FlagTerm(new Flags(Flags.Flag.SEEN), false);

    /**
     * Search mails that is from the specified address.
     * @param limit limited address
     * @return SearchTerm
     * @throws Exception
     */
    public static SearchTerm inAddress(String limit) throws Exception{
        Address address = new InternetAddress(limit);
        return new FromTerm(address);
    }

    /**
     * Search mails that from limited addresses.
     * @param limits limited address list
     * @return SearchTerm
     * @throws Exception
     */
    public static SearchTerm inAddress(List<String> limits) throws Exception{
        SearchTerm searchTerm = null;
        if (CollectionUtils.isNotEmpty(limits)) {
            FromTerm fromTerm[] = new FromTerm[limits.size()];
            for (int i = 0; i < limits.size(); i++) {
                Address address = new InternetAddress(limits.get(i));
                fromTerm[i] = new FromTerm(address);
            }
            searchTerm = new AndTerm(fromTerm);
        }
        return searchTerm;
    }
}
