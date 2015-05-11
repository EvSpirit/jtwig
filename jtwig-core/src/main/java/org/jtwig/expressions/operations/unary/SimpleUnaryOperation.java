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

package org.jtwig.expressions.operations.unary;

import org.jtwig.exception.CalculateException;
import org.jtwig.expressions.api.Expression;
import org.jtwig.expressions.api.UnaryOperation;
import org.jtwig.render.RenderContext;

public abstract class SimpleUnaryOperation implements UnaryOperation {
    @Override
    public Object apply(RenderContext context, Expression input) throws CalculateException {
        return apply(input.calculate(context));
    }

    protected abstract Object apply(Object input) throws CalculateException;
}
