/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.beans.factory.config;

import org.junit.Test;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Chris Beams
 */
public class Spr9552Tests {

	@Test
	public void childBeanShouldContainMergedMapEntriesFromParent() {
		DefaultListableBeanFactory bf = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader bdr = new XmlBeanDefinitionReader(bf);
		bdr.loadBeanDefinitions("classpath:org/springframework/beans/factory/config/Spr9552Tests-config.xml");
		FooBean foo = bf.getBean("childFoo", FooBean.class);

		// should contain entry added by child
		assertThat("missing key 'kChild'", foo.getMapBean().getMap().containsKey("kChild"), is(true));

		// should also contain entries added by parent
		assertThat("missing key 'kParent'", foo.getMapBean().getMap().containsKey("kParent"), is(true));
	}


	static class FooBean {

		private MapBean mapBean;

		public MapBean getMapBean() {
			return mapBean;
		}

		public void setMapBean(MapBean mapBean) {
			this.mapBean = mapBean;
		}
	}

	static class MapBean {
		private Map map;

		public void setMap(Map map) {
			this.map = map;
		}

		public Map getMap() {
			return map;
		}
	}
}
