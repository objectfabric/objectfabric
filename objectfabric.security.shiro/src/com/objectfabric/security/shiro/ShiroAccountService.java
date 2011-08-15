/**
 * Copyright (c) ObjectFabric Inc. All rights reserved.
 *
 * This file is part of ObjectFabric (objectfabric.com).
 *
 * ObjectFabric is licensed under the Apache License, Version 2.0, the terms
 * of which may be found at http://www.apache.org/licenses/LICENSE-2.0.html.
 *
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
 * WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */

package com.objectfabric.security.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;

import com.objectfabric.misc.PlatformAdapter;

/**
 * Apache Shiro is a powerful and easy-to-use Java security framework that performs
 * authentication, authorization, cryptography, and session management. This project
 * implements basic integration with ObjectFabric for a typical web site's accounts
 * management with roles.
 * <nl>
 * http://shiro.apache.org/index.html
 */
public class ShiroAccountService extends AccountService {

    private static final int DEFAULT_ITERATIONS = 733;

    private final OFRealm _realm;

    private final int _iterations;

    public ShiroAccountService(AccountStore store) {
        this(store, DEFAULT_ITERATIONS);
    }

    /**
     * The number of iterations the password should be hashed. Check
     * http://www.katasoft.com/blog/2011/04/04/strong-password-hashing-apache-shiro.
     */
    public ShiroAccountService(AccountStore store, int iterations) {
        super(store);

        _realm = new OFRealm(store);
        _iterations = iterations;

        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(Sha512Hash.ALGORITHM_NAME);
        matcher.setHashIterations(iterations);
        _realm.setCredentialsMatcher(matcher);

        SecurityManager securityManager = new DefaultSecurityManager(_realm);
        SecurityUtils.setSecurityManager(securityManager);
    }

    @Override
    protected Account createAccountImplementation(String username, String password) {
        byte[] salt = PlatformAdapter.createUID();
        Sha512Hash hash = new Sha512Hash(password, salt, _iterations);
        Account account = new Account(username);
        account.setSalt(salt);
        account.setPasswordHash(hash.getBytes());
        _realm.getStore().getAccounts().put(username, account);
        return account;
    }
}