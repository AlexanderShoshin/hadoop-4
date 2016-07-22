package shoshin.alex.data;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Alexander_Shoshin
 */
public class BiddingLog {
    private static String LOG_ENTRY_PATTERN = "^(\\S+)\\s(\\d+)\\s1\\s(\\S+)\\s(.*[\\d{1,3}\\.]{3}\\*)\\s(\\d+)\\s(\\d+)\\s(\\S+)\\s(\\S+)\\s(\\S+)\\snull\\s(\\S+)\\s(\\S+)\\s(\\S+)\\s(\\S+)\\s(\\S+)\\s(\\S+)\\s(\\S+)\\s(\\d+)\\s(\\d+)\\s(\\S+)\\s(\\S+)\\s(\\S+)$";
    private static int NUM_FIELDS = 21;
    private Matcher matcher;
    private UserAgentStringParser agentParser;
    
    private BiddingLog(Matcher matcher) {
        this.matcher = matcher;
        agentParser = UADetectorServiceFactory.getResourceModuleParser();
    }
    
    public static BiddingLog parse(String str) throws IllegalArgumentException {
        Pattern pattern = Pattern.compile(LOG_ENTRY_PATTERN);
        final Matcher matcher = pattern.matcher(str);
        if (!matcher.matches() || NUM_FIELDS != matcher.groupCount()) {
            throw new IllegalArgumentException();
        }
        
        return new BiddingLog(matcher);
    }

    public String getCityId() {
        return matcher.group(6);
    }

    public int getBidPrice() {
        return Integer.parseInt(matcher.group(17));
    }

    public String getUserAgent() {
        return matcher.group(4);
    }

    public String getOSFamily() {
        ReadableUserAgent userAgent = agentParser.parse(getUserAgent());
        return userAgent.getOperatingSystem().getFamilyName();
    }
}