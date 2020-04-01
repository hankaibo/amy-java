package cn.mypandora.springboot.config.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;

/**
 * EntityNotFoundException
 *
 * @author hankaibo
 * @date 2020/3/29
 */
public class EntityNotFoundException extends RuntimeException {
    private static final int NUM_2 = 2;

    public EntityNotFoundException(Class clazz, String... searchParamsMap) {
        super(EntityNotFoundException.generateMessage(clazz.getSimpleName(),
            toMap(String.class, String.class, searchParamsMap)));
    }

    private static String generateMessage(String entity, Map<String, String> searchParams) {
        return StringUtils.capitalize(entity) + " was not found for parameters " + searchParams;
    }

    private static <K, V> Map<K, V> toMap(Class<K> keyType, Class<V> valueType, Object... entries) {
        if (entries.length % NUM_2 == 1) {
            throw new IllegalArgumentException("Invalid entries");
        }
        return IntStream.range(0, entries.length / NUM_2).map(i -> i * NUM_2).collect(HashMap::new,
            (m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])), Map::putAll);
    }
}
