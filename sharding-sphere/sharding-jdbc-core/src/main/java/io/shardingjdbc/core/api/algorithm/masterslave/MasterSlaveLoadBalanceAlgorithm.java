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

package io.shardingjdbc.core.api.algorithm.masterslave;

import java.util.List;

/**
 * Master-slave database load-balance algorithm.
 *
 * @author zhangliang
 */
public interface MasterSlaveLoadBalanceAlgorithm {
    
    /**
     * Get data source.
     * 
     * @param name master-slave logic data source name
     * @param masterDataSourceName name of master data sources
     * @param slaveDataSourceNames names of slave data sources
     * @return name of selected data source
     */
    String getDataSource(String name, String masterDataSourceName, List<String> slaveDataSourceNames);
}
