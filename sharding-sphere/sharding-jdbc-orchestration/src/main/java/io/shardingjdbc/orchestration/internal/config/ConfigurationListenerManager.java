/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package io.shardingjdbc.orchestration.internal.config;

import io.shardingjdbc.core.exception.ShardingJdbcException;
import io.shardingjdbc.core.jdbc.core.datasource.MasterSlaveDataSource;
import io.shardingjdbc.core.jdbc.core.datasource.ShardingDataSource;
import io.shardingjdbc.orchestration.internal.listener.ListenerManager;
import io.shardingjdbc.orchestration.internal.state.datasource.DataSourceService;
import io.shardingjdbc.orchestration.reg.api.RegistryCenter;
import io.shardingjdbc.orchestration.reg.listener.DataChangedEvent;
import io.shardingjdbc.orchestration.reg.listener.EventListener;

import java.sql.SQLException;

/**
 * Configuration listener manager.
 *
 * @author caohao
 */
public final class ConfigurationListenerManager implements ListenerManager {
    
    private final ConfigurationNode configNode;
    
    private final RegistryCenter regCenter;
    
    private final ConfigurationService configService;
    
    private final DataSourceService dataSourceService;
    
    public ConfigurationListenerManager(final String name, final RegistryCenter regCenter) {
        configNode = new ConfigurationNode(name);
        this.regCenter = regCenter;
        configService = new ConfigurationService(name, regCenter);
        dataSourceService = new DataSourceService(name, regCenter);
    }
    
    @Override
    public void start(final ShardingDataSource shardingDataSource) {
        start(ConfigurationNode.DATA_SOURCE_NODE_PATH, shardingDataSource);
        start(ConfigurationNode.SHARDING_RULE_NODE_PATH, shardingDataSource);
        start(ConfigurationNode.SHARDING_PROPS_NODE_PATH, shardingDataSource);
    }
    
    private void start(final String node, final ShardingDataSource shardingDataSource) {
        String cachePath = configNode.getFullPath(node);
        regCenter.watch(cachePath, new EventListener() {
            
            @Override
            public void onChange(final DataChangedEvent event) {
                if (DataChangedEvent.Type.UPDATED == event.getEventType()) {
                    try {
                        shardingDataSource.renew(dataSourceService.getAvailableShardingRuleConfiguration().build(dataSourceService.getAvailableDataSources()), configService.loadShardingProperties());
                    } catch (final SQLException ex) {
                        throw new ShardingJdbcException(ex);
                    }
                }
            }
        });
    }
    
    @Override
    public void start(final MasterSlaveDataSource masterSlaveDataSource) {
        start(ConfigurationNode.DATA_SOURCE_NODE_PATH, masterSlaveDataSource);
        start(ConfigurationNode.MASTER_SLAVE_RULE_NODE_PATH, masterSlaveDataSource);
    }
    
    private void start(final String node, final MasterSlaveDataSource masterSlaveDataSource) {
        String cachePath = configNode.getFullPath(node);
        regCenter.watch(cachePath, new EventListener() {
            
            @Override
            public void onChange(final DataChangedEvent event) {
                if (DataChangedEvent.Type.UPDATED == event.getEventType()) {
                    masterSlaveDataSource.renew(dataSourceService.getAvailableMasterSlaveRuleConfiguration().build(dataSourceService.getAvailableDataSources()));
                }
            }
        });
    }
}
