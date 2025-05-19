import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class LRUCache{
	public static void main(String[] args){
		System.out.println("\n ********** This is LRU Cache ********** \n");

		/*
		 * What a cache contains
		 * 1. Get Ops
		 * 2. Put Ops
		 * 3. Eviction Strategy - Remove Least Recently Used (LRU)
		 */

		 /*
		  * Example:
			Size : 5
			Input: 1, 2, 3, 4, 5, 6, 3, 3, 2
			Output: 1 will be evicted
		  */

		LRUCacheImpl<Integer, String> lruCache = new LRUCacheImpl<Integer, String>(5);

		for(int i = 0; i < 10000; i++){
			lruCache.put(i, "Value " + i);
		}

		System.out.println(lruCache.get(0));
		System.out.println(lruCache.get(9999));

		// Test 9: Repeated gets don't affect order if no puts
		lruCache.put(1, "A");
		lruCache.put(2, "B");

		System.out.println(lruCache.get(1));
		System.out.println(lruCache.get(1));
		System.out.println(lruCache.get(1));

		lruCache.put(3, "C"); 

		System.out.println(lruCache.get(2));

		System.out.println("\n***************\n");

		LRUCacheImpl<Integer, Integer> cache1 = new LRUCacheImpl<>(5);

		cache1.put(1,1);
		cache1.put(2,1);
		cache1.put(3,1);
		cache1.put(4,1);
		cache1.put(5,1);
		cache1.put(6,1);
		cache1.put(3,1);
		cache1.put(3,1);
		cache1.put(2,1);

		System.out.println(cache1.getKeys());
	}
}

interface IMyCache<K, V>{

	public V get(K k);

	public void put(K k, V v);

	public List<K> getKeys();
}

class LRUCacheImpl<K, V> implements IMyCache<K, V>{

	int capacity;
	Map<K, V> cache;
	List<K> cacheKeys;

	LRUCacheImpl(int capacity){
		this.cache = new HashMap<K, V>();
		this.capacity = capacity;
		this.cacheKeys = new LinkedList<K>();
	}

	@Override
	public V get(K key){
		if(!cache.containsKey(key)){
			return null;
		}

		cacheKeys.remove(key);
		cacheKeys.addFirst(key);

		return cache.get(key);
	}

	@Override
	public void put(K key, V val){

		if(cache.containsKey(key)){
			cacheKeys.remove(key);
		} else if(cache.size() >=capacity){
			K lastKey = cacheKeys.getLast();
			cache.remove(lastKey);
		}

		cacheKeys.addFirst(key);
		cache.put(key, val);

		return;
	}

	@Override
	public List<K> getKeys(){
		return new ArrayList<>(cache.keySet());
	}
}