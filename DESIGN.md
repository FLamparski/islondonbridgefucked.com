IsLondonBridgeFucked.com:

1. As a commuter, I want to be able to know if London Bridge and the associated lines are utterly fucked.
2. As a 3G user, I want the website to be fast.
3. As someone who appreciates gratuitous profanity during times of high distress (such as when another fucking train is cancelled),
I want the site to reflect that
4. As someone who does NOT appreciate false positives, I want the site to be reasonably sure that London Bridge is fucked.

Determining fuckedness:

London Bridge will be fucked for a while. So I determined to use these 4 fuckedness levels:

1. SHITCON 1: TITSUP - Total Inability To Support Uncaring Passengers - Official disruption warning on Twitter
OR >50% of trains passing through LBG severely delayed or cancelled
2. SHITCON 2: FUBAR - >80% of all trains passing through LBG having some sort of delay, >5% having severe delays or cancellations.
3. SHITCON 3: > 20% of all trains having some sort of delay
4. SHITCON 4: SNAFU - all above do not apply, but London Bridge is still fucked.

Gathering data:

* Use the National Rail Darwin Push Port to get delay information
* Scan Twitter for posts with hashtag #LondonBridge and words like 'disruption' and not 'cleared'
* Sentiment analysis?

Architecture:

Dropwizard, static frontend site. Twitter4j.
