/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
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
 * </p>
 */

package io.shardingjdbc.orchestration.yaml;

import io.shardingjdbc.orchestration.api.config.OrchestrationConfiguration;
import io.shardingjdbc.orchestration.reg.etcd.EtcdConfiguration;
import io.shardingjdbc.orchestration.reg.zookeeper.ZookeeperConfiguration;
import lombok.Getter;
import lombok.Setter;

/**
 * Orchestration configuration for yaml.
 *
 * @author caohao
 */
@Getter
@Setter
public class YamlOrchestrationConfiguration {
    
    private String name;
    
    private EtcdConfiguration etcd;
    
    private ZookeeperConfiguration zookeeper;
    
    private boolean overwrite;
    
    /**
     * Get orchestration master-slave rule configuration from yaml.
     *
     * @return orchestration master-slave rule configuration from yaml
     */
    public OrchestrationConfiguration getOrchestrationConfiguration() {
        if (null != etcd && null != zookeeper) {
            throw new RuntimeException("Can't config both zookeeper and etcd as registry center!");
        }
        return new OrchestrationConfiguration(getName(), null != etcd ? etcd : zookeeper, overwrite);
    }
}
