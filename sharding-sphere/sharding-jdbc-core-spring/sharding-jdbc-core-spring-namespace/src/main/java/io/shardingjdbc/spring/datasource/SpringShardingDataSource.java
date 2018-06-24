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

package io.shardingjdbc.spring.datasource;

import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.jdbc.core.datasource.ShardingDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * Sharding datasource for spring namespace.
 *
 * @author caohao
 */
public class SpringShardingDataSource extends ShardingDataSource {
    
    public SpringShardingDataSource(final Map<String, DataSource> dataSourceMap, final ShardingRuleConfiguration shardingRuleConfig, 
                                    final Map<String, Object> configMap, final Properties props) throws SQLException {
        super(shardingRuleConfig.build(dataSourceMap), configMap, props);
    }
}
