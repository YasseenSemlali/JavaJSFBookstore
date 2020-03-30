/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gb4w20.arquillian.test;

import com.gb4w20.arquillian.test.rules.ParameterRule;
import com.gb4w20.arquillian.test.utils.BooksUtilities;
import com.gb4w20.gb4w20.backingbeans.UserSessionBean;
import com.gb4w20.gb4w20.entities.Authors;
import com.gb4w20.gb4w20.entities.Users;
import com.gb4w20.gb4w20.jpa.exceptions.NonexistentEntityException;
import com.gb4w20.gb4w20.jpa.exceptions.RollbackFailureException;
import com.gb4w20.gb4w20.filters.LoggedInUserFilter;
import com.gb4w20.gb4w20.entities.Books_;
import com.gb4w20.gb4w20.filters.ManagerFilter;
import com.gb4w20.gb4w20.jpa.GenresJpaController;
import com.gb4w20.gb4w20.jpa.UsersJpaController;
import com.gb4w20.gb4w20.jpa.exceptions.IllegalOrphanException;
import com.gb4w20.gb4w20.querybeans.NameAndNumberBean;
import com.gb4w20.gb4w20.querybeans.NameTotalAndCountBean;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;
import org.apache.commons.lang3.tuple.Pair;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 *
 * @author Yasseen
 */
@RunWith(Arquillian.class)
public abstract class ArquillianTestBase extends TestBase{    
    @Resource(lookup = "java:app/jdbc/bookstore_test")
    private DataSource dataSource;

    @Deployment
    public static WebArchive deploy() {

        // Use an alternative to the JUnit assert library called AssertJ
        // Need to reference MySQL driver as it is not part of either
        // embedded or remote
        final File[] dependencies = Maven
                .resolver()
                .loadPomFromFile("pom.xml")
                .resolve("mysql:mysql-connector-java",
                        "org.assertj:assertj-core",
                        "org.slf4j:slf4j-api",
                        "org.apache.logging.log4j:log4j-slf4j-impl",
                        "org.apache.logging.log4j:log4j-web",
                        "org.javatuples:javatuples"
                ).withTransitivity()
                .asFile();

        // The SQL script to create the database is in src/test/resources
        final WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
                .setWebXML(new File("src/main/webapp/WEB-INF/web-test.xml"))
                .addClass(UserSessionBean.class)
                .addPackage(IllegalOrphanException.class.getPackage())
                .addPackage(NonexistentEntityException.class.getPackage())
                .addPackage(UsersJpaController.class.getPackage())
                .addPackage(GenresJpaController.class.getPackage())
                .addPackage(RollbackFailureException.class.getPackage())
                .addPackage(NameTotalAndCountBean.class.getPackage())
                .addPackage(NameAndNumberBean.class.getPackage())
                .addPackage(BooksUtilities.class.getPackage())
                .addPackage(Books_.class.getPackage())
                .addPackage(Users.class.getPackage())
                .addPackage(Authors.class.getPackage())
                .addPackage(ArquillianTestBase.class.getPackage())
                .addPackage(ParameterRule.class.getPackage())
                .addPackage(Pair.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(new File("src/test/resources/WEB-INF/payara-resources.xml"), "payara-resources.xml")
                .addAsResource(new File("src/test/resources/META-INF/persistence.xml"), "META-INF/persistence.xml")
                .addAsResource(new File("src/main/resources/log4j2.xml"), "log4j2.xml")
                .addAsResource("testseed.sql")
                .addAsLibraries(dependencies);

        return webArchive;
    }

    @Override
    protected DataSource getDatasource() {
        return dataSource;
    }
}
