package org.mule.templates.util;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Test;

/**
 * 
 * @author Martin Ždila
 *
 */
public class AccountDateComparatorTest {
	
	private static final String KEY_LAST_MODIFIED_DATE = "LastModifiedDate";
	
	private static final String TEST_DATETIME_STRING = "2013-12-09T22:15:33.001Z";

	private static final String TEST_DATETIME_STRING2 = "2013-12-10T22:15:33.001Z";

	private static final DateTimeFormatter ISO_DATE_FORMATTER = ISODateTimeFormat.dateTimeParser();

	private static final Timestamp TEST_DATETIME_TIMESTAMP
			= new Timestamp(ISO_DATE_FORMATTER.parseDateTime(TEST_DATETIME_STRING).toDate().getTime());

	@Test(expected = IllegalArgumentException.class)
	public void nullsalesforceAccount() {
		Map<String, Object> salesforceAccount = null;

		Map<String, Object> databaseAccount = new HashMap<String, Object>();
		databaseAccount.put(KEY_LAST_MODIFIED_DATE, TEST_DATETIME_TIMESTAMP);

		AccountDateComparator.isAfter(salesforceAccount, databaseAccount);
	}

	@Test(expected = IllegalArgumentException.class)
	public void nulldatabaseAccount() {
		Map<String, Object> salesforceAccount = new HashMap<String, Object>();
		salesforceAccount.put(KEY_LAST_MODIFIED_DATE, TEST_DATETIME_STRING);

		Map<String, Object> databaseAccount = null;

		AccountDateComparator.isAfter(salesforceAccount, databaseAccount);
	}

	@Test(expected = IllegalArgumentException.class)
	public void malFormedsalesforceAccount() {
		Map<String, Object> salesforceAccount = new HashMap<String, Object>();

		Map<String, Object> databaseAccount = new HashMap<String, Object>();
		databaseAccount.put(KEY_LAST_MODIFIED_DATE, TEST_DATETIME_TIMESTAMP);

		AccountDateComparator.isAfter(salesforceAccount, databaseAccount);
	}

	public void emptydatabaseAccount() {
		Map<String, Object> salesforceAccount = new HashMap<String, Object>();
		salesforceAccount.put(KEY_LAST_MODIFIED_DATE, TEST_DATETIME_STRING);

		Map<String, Object> databaseAccount = new HashMap<String, Object>();

		Assert.assertTrue("The Salesforce account should be after the Database account",
				AccountDateComparator.isAfter(salesforceAccount, databaseAccount));
	}

	@Test
	public void salesforceAccountIsAfterdatabaseAccount() {
		Map<String, Object> salesforceAccount = new HashMap<String, Object>();
		salesforceAccount.put(KEY_LAST_MODIFIED_DATE, TEST_DATETIME_STRING2);

		Map<String, Object> databaseAccount = new HashMap<String, Object>();
		databaseAccount.put(KEY_LAST_MODIFIED_DATE, TEST_DATETIME_TIMESTAMP);

		Assert.assertTrue("The Salesforce account should be after the Database account",
				AccountDateComparator.isAfter(salesforceAccount, databaseAccount));
	}

	@Test
	public void salesforceAccountIsNotAfterdatabaseAccount() {
		Map<String, Object> salesforceAccount = new HashMap<String, Object>();
		salesforceAccount.put(KEY_LAST_MODIFIED_DATE, TEST_DATETIME_STRING);

		Map<String, Object> databaseAccount = new HashMap<String, Object>();
		databaseAccount.put(KEY_LAST_MODIFIED_DATE, TEST_DATETIME_TIMESTAMP);

		Assert.assertFalse("The Salesforce account should not be after the Database account",
				AccountDateComparator.isAfter(salesforceAccount, databaseAccount));
	}

	@Test
	public void salesforceAccountIsTheSameThatdatabaseAccount() {
		Map<String, Object> salesforceAccount = new HashMap<String, Object>();
		salesforceAccount.put(KEY_LAST_MODIFIED_DATE, TEST_DATETIME_STRING);

		Map<String, Object> databaseAccount = new HashMap<String, Object>();
		databaseAccount.put(KEY_LAST_MODIFIED_DATE, TEST_DATETIME_TIMESTAMP);

		Assert.assertFalse("The Salesforce account should not be after the Database account",
				AccountDateComparator.isAfter(salesforceAccount, databaseAccount));
	}

}
