package space.paraskun.postman.dataset;

import space.paraskun.postman.account.AbstractAccount;
import java.util.List;
import java.util.Map;

/**
 * Extracts data from specific source with given account and properties.
 */
@FunctionalInterface
public interface DataExtractor<T extends AbstractAccount> {
	List<Map<Object, Object>> extract(T account, Object... props)
			throws DataExtractionException;
}
