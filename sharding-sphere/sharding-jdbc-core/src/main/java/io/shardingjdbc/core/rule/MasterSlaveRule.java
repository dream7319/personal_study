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

package io.shardingjdbc.core.rule;

import io.shardingjdbc.core.api.algorithm.masterslave.MasterSlaveLoadBalanceAlgorithm;
import io.shardingjdbc.core.api.algorithm.masterslave.MasterSlaveLoadBalanceAlgorithmType;
import com.google.common.base.Preconditions;
import lombok.Getter;

import javax.sql.DataSource;
import java.util.Map;

/**
 * Databases and tables master-slave rule configuration.
 * 
 * @author zhangliang
 */
@Getter
public final class MasterSlaveRule {
    
    private final String name;
    
    private final String masterDataSourceName;
    
    private final DataSource masterDataSource;
    
    private final Map<String, DataSource> slaveDataSourceMap;
    
    private final MasterSlaveLoadBalanceAlgorithm strategy;
    
    public MasterSlaveRule(final String name, final String masterDataSourceName,
                           final DataSource masterDataSource, final Map<String, DataSource> slaveDataSourceMap) {
        this(name, masterDataSourceName, masterDataSource, slaveDataSourceMap, null);
    }
    
    public MasterSlaveRule(final String name, final String masterDataSourceName, 
                           final DataSource masterDataSource, final Map<String, DataSource> slaveDataSourceMap, final MasterSlaveLoadBalanceAlgorithm strategy) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(masterDataSourceName);
        Preconditions.checkNotNull(masterDataSource);
        Preconditions.checkNotNull(slaveDataSourceMap);
        Preconditions.checkState(!slaveDataSourceMap.isEmpty());
        this.name = name;
        this.masterDataSourceName = masterDataSourceName;
        this.masterDataSource = masterDataSource;
        this.slaveDataSourceMap = slaveDataSourceMap;
        this.strategy = null == strategy ? MasterSlaveLoadBalanceAlgorithmType.getDefaultAlgorithmType().getAlgorithm() : strategy;
    }
}
