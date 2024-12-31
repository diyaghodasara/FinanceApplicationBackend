package com.project.financeapp.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import javax.sql.DataSource;
import java.sql.Types;

@Configuration
public class SimpleJdbcConfig {
    @Bean(name = "SignUpUser")
    public SimpleJdbcCall registerUserCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withSchemaName("FinanceDB").withProcedureName("SignUpUser")
                .declareParameters(
                        new SqlParameter("in_email", Types.VARCHAR),
                        new SqlParameter("in_password_hash", Types.VARCHAR),
                        new SqlParameter("in_name", Types.VARCHAR),
                        new SqlOutParameter("out_message", Types.VARCHAR),
                        new SqlOutParameter("out_user_row", Types.OTHER)
                );
    }

    @Bean(name = "LoginUser")
    public SimpleJdbcCall loginUserCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withSchemaName("FinanceDB").withProcedureName("LoginUser")
                .declareParameters(
                        new SqlParameter("in_email", Types.VARCHAR),
                        new SqlOutParameter("out_user_row", Types.OTHER),
                        new SqlOutParameter("out_message", Types.VARCHAR)
                );
    }
}
