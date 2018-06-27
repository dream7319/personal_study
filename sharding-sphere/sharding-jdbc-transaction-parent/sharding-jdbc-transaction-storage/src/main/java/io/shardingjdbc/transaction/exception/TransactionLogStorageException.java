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

package io.shardingjdbc.transaction.exception;

/**
 * Transaction log storage exception.
 *
 * @author caohao
 */
public class TransactionLogStorageException extends RuntimeException {
    
    private static final long serialVersionUID = 3375637274979140802L;
    
    public TransactionLogStorageException(final String errorMessage, final Object... args) {
        super(String.format(errorMessage, args));
    }
    
    public TransactionLogStorageException(final String message, final Exception cause) {
        super(message, cause);
    }
    
    public TransactionLogStorageException(final Exception cause) {
        super(cause);
    }
}
