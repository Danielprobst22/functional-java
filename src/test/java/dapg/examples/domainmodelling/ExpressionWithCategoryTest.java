package dapg.examples.domainmodelling;

import lombok.NonNull;
import org.junit.jupiter.api.Test;

public class ExpressionWithCategoryTest {

    @Test
    void test() {
        Data validData = Data.of(new OldAwesomeValue());
        System.out.println("OldAwesomeValue: " + validData);
        System.out.println("OldAwesomeValue: " + validData.value().message());

        Data validData2 = Data.of(new NewAwesomeValue());
        System.out.println("NewAwesomeValue: " + validData2);
        System.out.println("NewAwesomeValue: " + validData2.value().message());

        /*
         * java: method "of" in record Data cannot be applied to given types;
         *   required: Expression<CategoryT>
         *   found:    InvalidValue
         *   reason: inference variable CategoryT has incompatible bounds
         *     equality constraints: Boring
         *     upper bounds: Awesome
         */
//        Data invalidData = Data.of(new InvalidValue());

        /*
         * java: method "of" in record Data cannot be applied to given types;
         *   required: Expression<CategoryT>
         *   found:    UnrelatedValue
         *   reason: cannot infer type-variable(s) CategoryT
         *     (argument mismatch; UnrelatedValue cannot be converted to Expression<CategoryT>)
         */
//        Data invalidData2 = Data.of(new UnrelatedValue());
    }

    //region Awesome
    public sealed interface Category {}

    public sealed interface Awesome extends Category {}

    public sealed interface OldAwesome extends Awesome permits OldAwesomeValue {}

    public sealed interface NewAwesome extends Awesome permits NewAwesomeValue {}

    public sealed interface Boring extends Category permits BoringValue {}
    //endregion


    public interface Expression<CategoryT extends Category> {

        CategoryT asCategory();

        default String message() {
            return switch (asCategory()) {
                case OldAwesome _ -> "I'm OldAwesome";
                case NewAwesome _ -> "I'm NewAwesome";
                case Boring _ -> "I'm Boring";
            };
        }
    }

    //region Value implementations
    public record OldAwesomeValue() implements OldAwesome, Expression<OldAwesome> {
        @Override
        public OldAwesome asCategory() {
            return this;
        }
    }

    public record NewAwesomeValue() implements NewAwesome, Expression<NewAwesome> {
        @Override
        public NewAwesome asCategory() {
            return this;
        }
    }

    public record BoringValue() implements Boring, Expression<Boring> {
        @Override
        public Boring asCategory() {
            return this;
        }
    }

    public record UnrelatedValue() {}
    //endregion

    public record Data(
            Expression<Awesome> value
    ) {
        public static <CategoryT extends Awesome> Data of(Expression<CategoryT> expression) {
            return new Data(ValueMustBeAnAwesomeExpression.enforce(expression));
        }

        private static final class ValueMustBeAnAwesomeExpression {
            public static Expression<Awesome> enforce(@NonNull Expression<? extends Awesome> expression) {
                //noinspection unchecked
                return (Expression<Awesome>) expression;
            }
        }
    }
}
