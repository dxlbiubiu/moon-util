package com.moon.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.moon.lang.ThrowUtil.noInstanceError;

/**
 * @author benshaoye
 * @date 2018/9/12
 */
public class CollectUtil extends BaseCollectUtil {

    protected CollectUtil() {
        noInstanceError();
    }

    public final static <E, C extends Collection<E>> int size(C collect) {
        return collect == null ? 0 : collect.size();
    }

    public final static int sizeByObject(Object collect) {
        return collect == null ? 0 : ((Collection) collect).size();
    }

    public final static <E, C extends Collection<E>> boolean isEmpty(C collect) {
        return collect == null || collect.isEmpty();
    }

    public final static <E, C extends Collection<E>> boolean isNotEmpty(C collect) {
        return !isEmpty(collect);
    }

    /*
     * ---------------------------------------------------------------------------------
     * adders
     * ---------------------------------------------------------------------------------
     */

    public final static <E, C extends Collection<E>> C add(C collect, E element) {
        if (collect != null) {
            collect.add(element);
        }
        return collect;
    }

    public final static <E, C extends Collection<E>> C add(C collect, E element1, E element2) {
        if (collect != null) {
            collect.add(element1);
            collect.add(element2);
        }
        return collect;
    }

    public final static <E, C extends Collection<E>> C addAll(C collect, E... elements) {
        if (collect != null) {
            for (E element : elements) {
                collect.add(element);
            }
        }
        return collect;
    }

    public final static <E, C extends Collection<E>> C addAll(C collect, Collection<E> collection) {
        if (collect != null) {
            collect.addAll(collection);
        }
        return collect;
    }

    public final static <E, C extends Collection<E>> C addAll(C collect, Iterable<E> iterable) {
        if (collect != null) {
            for (E elem : iterable) {
                collect.add(elem);
            }
        }
        return collect;
    }

    /*
     * ---------------------------------------------------------------------------------
     * add if non null, 只有当带插入项非空时才执行插入操作
     * ---------------------------------------------------------------------------------
     */


    public final static <E, C extends Collection<E>> C addIfNonNull(C collect, E item) {
        if (collect != null && item != null) {
            collect.add(item);
        }
        return collect;
    }

    public final static <E, C extends Collection<E>> C addIfNonNull(C collect, E item1, E item2) {
        if (collect != null) {
            if (item1 != null) {
                collect.add(item1);
            }
            if (item2 != null) {
                collect.add(item2);
            }
        }
        return collect;
    }

    public final static <E, C extends Collection<E>> C addIfNonNull(C collect, E... items) {
        if (collect != null) {
            E item;
            for (int i = 0; i < items.length; i++) {
                if ((item = items[i]) != null) {
                    collect.add(item);
                }
            }
        }
        return collect;
    }

    public final static <E, C extends Collection<E>> C addIfNonNull(C collect, Iterable<E> iterable) {
        if (collect != null) {
            for (E elem : iterable) {
                if (elem != null) {
                    collect.add(elem);
                }
            }
        }
        return collect;
    }

    /*
     * ---------------------------------------------------------------------------------
     * converter
     * ---------------------------------------------------------------------------------
     */

    public final static <T, O, C1 extends Collection<T>> Collection<O> map(C1 src, Function<? super T, O> function) {
        return IteratorUtil.map(src, function);
    }

    public final static <T> Collection<T> concat(Collection<T> collection, Collection<T>... collections) {
        return concat0(collection, collections);
    }

    public final static <T> Set<T> toSet(T... items) {
        return SetUtil.ofHashSet(items);
    }

    public final static <T> List<T> toList(T... items) {
        return ListUtil.ofArrayList(items);
    }

    public final static <E, T> T[] toArray(Collection<E> collection, Class<T> componentType) {
        int index = 0;
        Object array = Array.newInstance(componentType, collection.size());
        for (Object item : collection) {
            Array.set(array, index++, item);
        }
        return (T[]) array;
    }

    /*
     * ---------------------------------------------------------------------------------
     * contains
     * ---------------------------------------------------------------------------------
     */

    public final static <T> boolean contains(Collection<T> collection, T item) {
        return collection != null && collection.contains(item);
    }

    public final static <T> boolean containsAny(Collection<T> collect, T item1, T item2) {
        return collect != null && (collect.contains(item1) || collect.contains(item2));
    }

    public final static <T> boolean containsAll(Collection<T> collect, T item1, T item2) {
        return collect != null && (collect.contains(item1) && collect.contains(item2));
    }

    public final static <T> boolean containsAny(Collection<T> collect, T... items) {
        if (collect == null) {
            return false;
        }
        for (int i = 0, l = items.length; i < l; i++) {
            if (collect.contains(items[i])) {
                return true;
            }
        }
        return false;
    }

    public final static <T> boolean containsAll(Collection<T> collect, T... items) {
        if (collect == null) {
            return false;
        }
        for (int i = 0, l = items.length; i < l; i++) {
            if (!collect.contains(items[i])) {
                return false;
            }
        }
        return true;
    }

    public final static <T> boolean containsAny(Collection<T> collect1, Collection<T> collect2) {
        if (collect1 == collect2) {
            return true;
        }
        int size1 = collect1.size(), size2 = collect2.size();
        Collection<T> large = size1 > size2 ? collect1 : collect2;
        Collection<T> small = size1 > size2 ? collect2 : collect1;
        for (T item : large) {
            if (small.contains(item)) {
                return true;
            }
        }
        return false;
    }

    public final static <T> boolean containsAll(Collection<T> collect1, Collection<T> collect2) {
        return collect1 == collect2 ? true : (collect1 != null && collect1.containsAll(collect2));
    }

    /*
     * ---------------------------------------------------------------------------------
     * matchers
     * ---------------------------------------------------------------------------------
     */

    public final static <T> boolean matchAny(Collection<T> collect, Predicate<T> matcher) {
        if (collect != null) {
            for (T item : collect) {
                if (matcher.test(item)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final static <T> boolean matchAll(Collection<T> collect, Predicate<T> matcher) {
        if (collect != null) {
            for (T item : collect) {
                if (!matcher.test(item)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
