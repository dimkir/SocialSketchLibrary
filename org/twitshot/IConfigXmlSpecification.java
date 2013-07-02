package org.twitshot;
/**
 *  These are constants specified for the xml file.
 * AND!! These are also KEYS which are used by the Map<> data
 * structure inside of others to distinguish which keys are which inside of map.
 *  And also specification can go here.
 */
interface IConfigXmlSpecification
{
   static String C_PROFILE_TAG = "profile";
   
   static String C_CONSUMER_KEY    = "consumerKey";
   static String C_CONSUMER_SECRET = "consumerSecret";
   
   static String C_OAUTH_TOKEN     = "oAuthToken";
   static String C_OAUTH_SECRET    = "oAuthSecret";
    
}

/*
 * What format should the xml be like? #=
 * first let's try the simpliest:
 * 
<?xml version="1.0" encoding="UTF-8"?>
<root>
    <profile>
        <consumerKey>VctBzerp3P3Wg3oFFBA8NA</consumerKey>
        <consumerSecret>d4oliVwCrwiO5QO8p95kSygZ6Q4C6pQNXJn1IAyag</consumerSecret>
        <oAuthToken>1413163736-swd4b1RCjMJZLQOmmOJ4zkWKixwcYIAG3LinfVs</oAuthToken>
        <oAuthSecret>eG5gnsMj3UTxu9Q96GvzWaetgG4mOFlHYBJCVGCmPg</oAuthSecret>
    </profile>
</root>

 * 
 * 
 * 
 */