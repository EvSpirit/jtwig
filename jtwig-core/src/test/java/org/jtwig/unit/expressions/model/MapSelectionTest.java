/**
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
 */

package org.jtwig.unit.expressions.model;

import java.util.ArrayList;
import java.util.HashMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import org.jtwig.unit.AbstractJtwigTest;
import org.jtwig.exception.CalculateException;
import org.jtwig.expressions.api.CompilableExpression;
import org.jtwig.expressions.api.Expression;
import org.jtwig.expressions.model.Constant;
import org.jtwig.expressions.model.MapSelection;
import org.jtwig.expressions.model.Variable;
import org.jtwig.render.RenderContext;
import static org.jtwig.types.Undefined.UNDEFINED;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MapSelectionTest extends AbstractJtwigTest {
    @Test
    public void selection() throws Exception {
        Variable variable = mock(Variable.class);
        CompilableExpression key = new Constant<>("test");

        when(variable.compile(compileContext)).thenReturn(mapExpression("test", "value"));

        MapSelection selection = new MapSelection(null, variable, key);
        Expression compiled = selection.compile(compileContext);

        assertThat(compiled, notNullValue(Expression.class));
        assertEquals("value", compiled.calculate(renderContext));
    }

    @Test(expected = CalculateException.class)
    public void undefinedKey() throws Exception {
        Variable variable = mock(Variable.class);
        CompilableExpression key = new Constant<>(UNDEFINED);

        when(variable.compile(compileContext)).thenReturn(mapExpression("test", "value"));

        MapSelection selection = new MapSelection(null, variable, key);
        Expression compiled = selection.compile(compileContext);

        assertThat(compiled, notNullValue(Expression.class));
        compiled.calculate(renderContext);
    }


    @Test(expected = CalculateException.class)
    public void nonMapVariable() throws Exception {
        Variable variable = mock(Variable.class);
        CompilableExpression key = new Constant<>("test");

        when(variable.compile(compileContext)).thenReturn(new Expression() {
            @Override
            public Object calculate(RenderContext context) throws CalculateException {
                return new ArrayList<>();
            }
        });

        MapSelection selection = new MapSelection(null, variable, key);
        Expression compiled = selection.compile(compileContext);

        assertThat(compiled, notNullValue(Expression.class));
        compiled.calculate(renderContext);
    }

    private Expression mapExpression(final String key, final String value) {
        return new Expression() {
            @Override
            public Object calculate(RenderContext context) throws CalculateException {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(key, value);
                return map;
            }
        };
    }
}
