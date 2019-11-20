create schema calculator;
CREATE TABLE calculator.calculatons (
                                        id serial primary key,
                                        num1 varchar(256),
                                        num2 varchar(256),
                                        operation varchar(256),
                                        result varchar(256)
);