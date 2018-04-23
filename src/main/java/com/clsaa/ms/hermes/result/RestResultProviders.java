package com.clsaa.ms.hermes.result;


import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 客户端可以动态注册业务码解析器，每次调用都是线程安全的
 */
final class RestResultProviders {
	private RestResultProviders() {
		throw new UnsupportedOperationException();
	}

	private static final List<RestResultProvider> restResultProviders =
			new CopyOnWriteArrayList<>();

	static {
		// 自动加载业务码解析器
		ServiceLoader<RestResultProvider> providerLoader =
				ServiceLoader.load(RestResultProvider.class);
		if (providerLoader != null) {
			Iterator<RestResultProvider> providerIterator = providerLoader.iterator();
			if (providerIterator != null) {
				while (providerIterator.hasNext()) {
					add(providerIterator.next());
				}
			}
		}
	}

	/**
	 * 动态添加RestResult解析器，此方法是线程安全的
	 *
	 * @author 任贵杰
	 */
	static void add(RestResultProvider provider) {
		if (provider == null) {
			return;
		}
		restResultProviders.add(provider);
	}

	/**
	 * 将某个source适配成RestResult(code 和message)，如果source不能解析，则默认返回一个 业务码，code=UNKNOWN，message为source
	 * class的名字或者空。
	 *
	 * @return never return null
	 */
	static RestResult getRestResult(Object source) {
		if (source != null && !restResultProviders.isEmpty()) {
			for (RestResultProvider provider : restResultProviders) {
				if (provider.support(source)) {
					RestResult rr = provider.produce(source);
					if (rr == null) {
						continue;
					}
					return rr;
				}
			}
		}
		return new StandardRestResult(
				source == null ? "" : source.getClass().getSimpleName());
	}
}
