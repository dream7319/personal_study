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

package io.shardingjdbc.orchestration.internal.jdbc.connection;

import io.shardingjdbc.core.jdbc.unsupported.AbstractUnsupportedOperationConnection;
import io.shardingjdbc.orchestration.internal.jdbc.metadata.CircuitBreakerDatabaseMetaData;
import io.shardingjdbc.orchestration.internal.jdbc.statement.CircuitBreakerPreparedStatement;
import io.shardingjdbc.orchestration.internal.jdbc.statement.CircuitBreakerStatement;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

/**
 * Circuit breaker connection.
 * 
 * @author caohao
 */
public final class CircuitBreakerConnection extends AbstractUnsupportedOperationConnection {
    
    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return new CircuitBreakerDatabaseMetaData();
    }
    
    @Override
    public void setReadOnly(final boolean readOnly) throws SQLException {
        
    }
    
    @Override
    public boolean isReadOnly() throws SQLException {
        return false;
    }
    
    @Override
    public void setTransactionIsolation(final int level) throws SQLException {
        
    }
    
    @Override
    public int getTransactionIsolation() throws SQLException {
        return Connection.TRANSACTION_NONE;
    }
    
    @Override
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }
    
    @Override
    public void clearWarnings() throws SQLException {
        
    }
    
    @Override
    public void setAutoCommit(final boolean autoCommit) throws SQLException {
        
    }
    
    @Override
    public boolean getAutoCommit() throws SQLException {
        return false;
    }
    
    @Override
    public void commit() throws SQLException {
        
    }
    
    @Override
    public void rollback() throws SQLException {
        
    }
    
    @Override
    public void setHoldability(final int holdability) throws SQLException {
        
    }
    
    @Override
    public int getHoldability() throws SQLException {
        return 0;
    }
    
    @Override
    public PreparedStatement prepareStatement(final String sql) throws SQLException {
        return new CircuitBreakerPreparedStatement();
    }
    
    @Override
    public PreparedStatement prepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        return new CircuitBreakerPreparedStatement();
    }
    
    @Override
    public PreparedStatement prepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        return new CircuitBreakerPreparedStatement();
    }
    
    @Override
    public PreparedStatement prepareStatement(final String sql, final int autoGeneratedKeys) throws SQLException {
        return new CircuitBreakerPreparedStatement();
    }
    
    @Override
    public PreparedStatement prepareStatement(final String sql, final int[] columnIndexes) throws SQLException {
        return new CircuitBreakerPreparedStatement();
    }
    
    @Override
    public PreparedStatement prepareStatement(final String sql, final String[] columnNames) throws SQLException {
        return new CircuitBreakerPreparedStatement();
    }
    
    @Override
    public Statement createStatement() throws SQLException {
        return new CircuitBreakerStatement();
    }
    
    @Override
    public Statement createStatement(final int resultSetType, final int resultSetConcurrency) throws SQLException {
        return new CircuitBreakerStatement();
    }
    
    @Override
    public Statement createStatement(final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        return new CircuitBreakerStatement();
    }
    
    @Override
    public void close() throws SQLException {
    }
    
    @Override
    public boolean isClosed() throws SQLException {
        return false;
    }
}
