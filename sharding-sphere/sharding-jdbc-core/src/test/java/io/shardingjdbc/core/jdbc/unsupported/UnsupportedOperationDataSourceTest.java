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

package io.shardingjdbc.core.jdbc.unsupported;

import io.shardingjdbc.core.common.base.AbstractShardingJDBCDatabaseAndTableTest;
import io.shardingjdbc.core.constant.DatabaseType;
import org.junit.Test;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;

public final class UnsupportedOperationDataSourceTest extends AbstractShardingJDBCDatabaseAndTableTest {
    
    public UnsupportedOperationDataSourceTest(final DatabaseType databaseType) {
        super(databaseType);
    }
    
    @Test(expected = SQLFeatureNotSupportedException.class)
    public void assertGetLoginTimeout() throws SQLException {
        getShardingDataSource().getLoginTimeout();
    }
    
    @Test(expected = SQLFeatureNotSupportedException.class)
    public void assertSetLoginTimeout() throws SQLException {
        getShardingDataSource().setLoginTimeout(0);
    }
}
