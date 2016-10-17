package io.pivotal.spring.cloud.localconfig;

import io.pivotal.spring.cloud.service.common.GemfireServiceInfo;
import org.springframework.cloud.localconfig.LocalConfigServiceInfoCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by esuez on 1/17/16.
 */
public class GemfireServiceInfoCreator extends LocalConfigServiceInfoCreator<GemfireServiceInfo> {
	private static final Pattern pattern = Pattern.compile("gemfire:\\/\\/"
            + "(((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}" // domain name
            + "|"
            + "localhost" // localhost
            + "|"
            + "(([0-9]{1,3}\\.){3})[0-9]{1,3})" // ip
            + ":"
            + "(\\d+)\\/*"); // port

	public GemfireServiceInfoCreator() {
		super("gemfire");
	}

	@Override
	public GemfireServiceInfo createServiceInfo(String id, String uri) {
		String locatorString = extractLocatorFromUri(uri);
		List<String> locators = new ArrayList<String>();
		locators.add(locatorString);
		return new GemfireServiceInfo(id, locators);
	}

	
	public static String extractLocatorFromUri(String uri) {
		Matcher matcher = pattern.matcher(uri);
		if (!matcher.matches()) {
			throw new IllegalArgumentException(
					"Could not parse locator uri. Expected format gemfire://host:port, received: " + uri);
		}
		return matcher.group(1) + "[" + matcher.group(5) + "]";
	}
}