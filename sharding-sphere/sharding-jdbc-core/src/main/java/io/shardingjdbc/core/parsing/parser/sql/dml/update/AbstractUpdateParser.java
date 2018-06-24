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

package io.shardingjdbc.core.parsing.parser.sql.dml.update;

import io.shardingjdbc.core.rule.ShardingRule;
import io.shardingjdbc.core.parsing.lexer.LexerEngine;
import io.shardingjdbc.core.parsing.lexer.token.DefaultKeyword;
import io.shardingjdbc.core.parsing.lexer.token.Keyword;
import io.shardingjdbc.core.parsing.parser.clause.facade.AbstractUpdateClauseParserFacade;
import io.shardingjdbc.core.parsing.parser.context.selectitem.SelectItem;
import io.shardingjdbc.core.parsing.parser.sql.SQLParser;
import io.shardingjdbc.core.parsing.parser.sql.dml.DMLStatement;
import lombok.RequiredArgsConstructor;

import java.util.Collections;

/**
 * Update parser.
 *
 * @author zhangliang
 */
@RequiredArgsConstructor
public abstract class AbstractUpdateParser implements SQLParser {
    
    private final ShardingRule shardingRule;
    
    private final LexerEngine lexerEngine;
    
    private final AbstractUpdateClauseParserFacade updateClauseParserFacade;
    
    @Override
    public DMLStatement parse() {
        lexerEngine.nextToken();
        lexerEngine.skipAll(getSkippedKeywordsBetweenUpdateAndTable());
        lexerEngine.unsupportedIfEqual(getUnsupportedKeywordsBetweenUpdateAndTable());
        DMLStatement result = new DMLStatement();
        updateClauseParserFacade.getTableReferencesClauseParser().parse(result, true);
        updateClauseParserFacade.getUpdateSetItemsClauseParser().parse(result);
        lexerEngine.skipUntil(DefaultKeyword.WHERE);
        updateClauseParserFacade.getWhereClauseParser().parse(shardingRule, result, Collections.<SelectItem>emptyList());
        return result;
    }
    
    protected Keyword[] getSkippedKeywordsBetweenUpdateAndTable() {
        return new Keyword[0];
    }
    
    protected Keyword[] getUnsupportedKeywordsBetweenUpdateAndTable() {
        return new Keyword[0];
    }
}
