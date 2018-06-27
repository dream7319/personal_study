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

package io.shardingjdbc.core.parsing.parser.dialect.oracle.sql;

import io.shardingjdbc.core.rule.ShardingRule;
import io.shardingjdbc.core.parsing.lexer.LexerEngine;
import io.shardingjdbc.core.parsing.parser.dialect.oracle.clause.facade.OracleUpdateClauseParserFacade;
import io.shardingjdbc.core.parsing.parser.sql.dml.update.AbstractUpdateParser;

/**
 * Update parser for Oracle.
 * 
 * @author zhangliang 
 */
public class OracleUpdateParser extends AbstractUpdateParser {
    
    public OracleUpdateParser(final ShardingRule shardingRule, final LexerEngine lexerEngine) {
        super(shardingRule, lexerEngine, new OracleUpdateClauseParserFacade(shardingRule, lexerEngine));
    }
}
