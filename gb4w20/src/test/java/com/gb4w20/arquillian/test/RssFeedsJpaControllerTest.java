package com.gb4w20.arquillian.test;

import com.gb4w20.gb4w20.entities.RssFeeds;
import com.gb4w20.gb4w20.jpa.RssFeedsJpaController;
import javax.inject.Inject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * Used to test various methods inside the RssFeedsJpaController class. 
 * This does not include the methods that were auto generated 
 * by NetBeans (create, edit, delete). 
 * @author Jasmar Badion
 */
@RunWith(Enclosed.class)
public class RssFeedsJpaControllerTest {
    
    /**
     * Used to run valid tests for the GetActiveFeed 
     * method inside of the RssFeedsJpaController class. 
     * @author Jasmar Badion
     */
    public static class GetActiveFeed extends ArquillianTestBase {
        
        @Inject
        private RssFeedsJpaController rssFeedsJpaController;
        
        //holds the resukt of the method being tested
        private RssFeeds rssFeeds;
        
        /**
         * Initializing the the result of the method being tested
         * @author Jasmar Badion
         */
        @Before
        public void initTheMethodResult(){
            this.rssFeeds = rssFeedsJpaController.getActiveFeed();
        }
        
        /**
         * Tests if it's returning the correct RSS feed
         */
        @Test
        public void testCorrectActiveFeeds(){
            String expectedRssUrl = "https://www.cbc.ca/cmlink/rss-topstories";
            
            assertEquals("Did not get the right RSS feed", expectedRssUrl, this.rssFeeds.getUrl());
        }
    }
    
}