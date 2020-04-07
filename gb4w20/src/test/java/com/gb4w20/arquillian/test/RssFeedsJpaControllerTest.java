package com.gb4w20.arquillian.test;

import com.gb4w20.gb4w20.entities.RssFeeds;
import com.gb4w20.gb4w20.jpa.RssFeedsJpaController;
import javax.inject.Inject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * <h1>RSS Feeds JPA Controller Test</h1>
 * <p>
 * Used to test various methods inside the RssFeedsJpaController class. 
 * This does not include the methods that were auto generated 
 * by NetBeans (create, edit, delete). 
 * </p>
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
         * @author Jasmar Badion
         */
        @Test
        public void testCorrectActiveFeeds(){
            String expectedRssUrl = "https://www.cbc.ca/cmlink/rss-topstories";
            
            assertEquals("Did not get the right RSS feed", expectedRssUrl, this.rssFeeds.getUrl());
        }
        
        /**
         * Testing when the only active RSS is set to disabled then 
         * the method should return null
         * @throws Exception 
         * @author Jasmar Badion
         */
        @Test
        public void testNullWhenNoActiveFeeds() throws Exception{
            //disabling the only active RSS feed
            this.rssFeeds.setEnabled(false);
            this.rssFeedsJpaController.edit(rssFeeds);
            
            RssFeeds noActiveRss = this.rssFeedsJpaController.getActiveFeed();
            
            //enabling the previous RSS back to active
            this.rssFeeds.setEnabled(true);
            this.rssFeedsJpaController.edit(rssFeeds);
            
            assertNull("Supposed to return null but it's not", noActiveRss);
        }
    }
    
}