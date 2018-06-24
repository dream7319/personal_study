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

package io.shardingjdbc.transaction.bed.async;

import com.dangdang.ddframe.job.api.JobConfiguration;
import com.dangdang.ddframe.job.api.JobScheduler;
import io.shardingjdbc.transaction.api.config.AbstractBestEffortsDeliveryJobConfiguration;
import io.shardingjdbc.transaction.api.config.SoftTransactionConfiguration;
import io.shardingjdbc.transaction.storage.TransactionLogStorageFactory;
import com.dangdang.ddframe.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.reg.zookeeper.ZookeeperRegistryCenter;
import lombok.RequiredArgsConstructor;

/**
 * Best efforts delivery B.A.S.E transaction asynchronized job abstract factory.
 * 
 * @author zhangliang
 */
@RequiredArgsConstructor
public abstract class AbstractBestEffortsDeliveryJobFactory<T extends AbstractBestEffortsDeliveryJobConfiguration> {
    
    private final SoftTransactionConfiguration transactionConfig;
    
    /**
     * initialize asynchronized job.
     */
    public final void init() {
        @SuppressWarnings("unchecked")
        T bedJobConfig = (T) transactionConfig.getBestEffortsDeliveryJobConfiguration().get();
        CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(createZookeeperConfiguration(bedJobConfig));
        regCenter.init();
        JobScheduler jobScheduler = new JobScheduler(regCenter, createBedJobConfiguration(bedJobConfig));
        jobScheduler.setField("transactionConfig", transactionConfig);
        jobScheduler.setField("transactionLogStorage", TransactionLogStorageFactory.createTransactionLogStorage(transactionConfig.buildTransactionLogDataSource()));
        jobScheduler.init();
    }
    
    protected abstract ZookeeperConfiguration createZookeeperConfiguration(T config);
    
    private JobConfiguration createBedJobConfiguration(final T bedJobConfig) {
        JobConfiguration result = new JobConfiguration(bedJobConfig.getJobName(), NestedBestEffortsDeliveryJob.class, 1, bedJobConfig.getCron());
        result.setFetchDataCount(bedJobConfig.getTransactionLogFetchDataCount());
        return result;
    }
}
