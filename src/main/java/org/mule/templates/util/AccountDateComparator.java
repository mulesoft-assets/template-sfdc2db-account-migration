/**
 * Mule Anypoint Template
 * Copyright (c) MuleSoft, Inc.
 * All rights reserved.  http://www.mulesoft.com
 */

package org.mule.templates.util;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 * The function of this class is to establish a relation happens before between two maps - first representing DB and second SFDC account.
 * 
 * It's assumed that these maps are well formed maps thus they both contain an entry with the expected key. Never the less validations are being done.
 * 
 * @author martin.zdila
 */
public class AccountDateComparator {
	private static final String LAST_MODIFIED_DATE = "LastModifiedDate";

	/**
	 * Validate which account has the latest last referenced date.
	 * 
	 * @param salesforceAccount
	 *             map
	 * @param databaseAccount
	 *            Database account map
	 * @return true if the last activity date from salesforceAccount is after the one from databaseAccount
	 */
	public static boolean isAfter(Map<String, ?> salesforceAccount, Map<String, ?> databaseAccount) {
		Validate.notNull(salesforceAccount, "Salesforce account must not be null");
		Validate.notNull(databaseAccount, "Database account must not be null");
		
		Validate.isTrue(salesforceAccount.containsKey(LAST_MODIFIED_DATE), "The Salesforce account map must contain the key " + LAST_MODIFIED_DATE);

		if (databaseAccount.get(LAST_MODIFIED_DATE) == null) {
			return true;
		}
		
		DateTimeFormatter formatter = ISODateTimeFormat.dateTimeParser();
		Object maybeSalesforceDate = salesforceAccount.get(LAST_MODIFIED_DATE);
		Validate.isTrue(maybeSalesforceDate instanceof String, "LastModifiedDate of Salesforce account must be String");
		DateTime salesforceAccountLastModifiedDate = formatter.parseDateTime((String) maybeSalesforceDate);

		Object maybeDatabaseDate = databaseAccount.get(LAST_MODIFIED_DATE);
		Validate.isTrue(maybeDatabaseDate instanceof Date, "LastModifiedDate of Database account must be java.util.Date or subclass");
		DateTime databaseAccountLastModifiedDate = new DateTime((Date) maybeDatabaseDate);
		
		return salesforceAccountLastModifiedDate.isAfter(databaseAccountLastModifiedDate);
	}
}
