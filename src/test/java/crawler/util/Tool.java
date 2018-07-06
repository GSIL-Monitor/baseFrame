package crawler.util;

import java.util.List;

public class Tool {

	public static <T> List<T> getSubListPage(List<T> list, int skip,
            int pageSize) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        int startIndex = skip;
        int endIndex = skip + pageSize;
        if (startIndex > endIndex || startIndex > list.size()) {
            return null;
        }
        if (endIndex > list.size()) {
            endIndex = list.size();
        }
        return list.subList(startIndex, endIndex);
    }
}
