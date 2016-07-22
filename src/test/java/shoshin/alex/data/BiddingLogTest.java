package shoshin.alex.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Alexander_Shoshin
 */
public class BiddingLogTest {
    @Test
    public void should_extract_price_and_cityId() {
        BiddingLog biddingLog = BiddingLog.parse("709b7cffadaefd399738439386ece34d	20131020104600653	1	CAOI72FHgYj	Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)	123.88.187.*	216	1	2	abc5a8aaa90bafdd7d6b60683e6f7ed3	a6e53cda7ceff62ea2dac7f281446fa5	null	4254532549	300	250	FirstView	Na	5	7323	277	84	null	2259	11278,13800,10684,13042,10006,10110,10123,13776,10031,10063");
        Assert.assertEquals(biddingLog.getBidPrice(), 277);
        Assert.assertEquals(biddingLog.getCityId(), "1");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void should_throw_exception_when_wrong_data() {
        BiddingLog.parse("bad input data");
    }
}
