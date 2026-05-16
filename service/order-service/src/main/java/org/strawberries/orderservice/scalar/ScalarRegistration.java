package org.strawberries.orderservice.scalar;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsRuntimeWiring;
import graphql.scalars.ExtendedScalars;
import graphql.schema.idl.RuntimeWiring;

@DgsComponent
public class ScalarRegistration {
    @DgsRuntimeWiring
    public RuntimeWiring.Builder addScalars(RuntimeWiring.Builder builder) {
        return builder
                .scalar(ExtendedScalars.GraphQLBigDecimal)
                .scalar(ExtendedScalars.UUID)
                .scalar(ExtendedScalars.DateTime)
                .scalar(ExtendedScalars.GraphQLLong);
    }
}
