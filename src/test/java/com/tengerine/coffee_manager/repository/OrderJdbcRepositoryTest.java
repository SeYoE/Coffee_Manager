package com.tengerine.coffee_manager.repository;

import com.tengerine.coffee_manager.model.*;
import com.tengerine.coffee_manager.model.Order;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class OrderJdbcRepositoryTest {

    static EmbeddedMysql embeddedMysql;

    @BeforeAll
    void init() {
        var mysqlConfig = MysqldConfig.aMysqldConfig(Version.v8_0_17)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = EmbeddedMysql.anEmbeddedMysql(mysqlConfig)
                .addSchema("test-order_mgmt", ScriptResolver.classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    void destroy() {
        embeddedMysql.stop();
    }

    @Autowired
    OrderJdbcRepository repository;

    private static OrderItem newOrderItem = new OrderItem(
            UUID.fromString("8587a4d7-ad57-43e5-8a60-e30d0f77a3ad"),
            Category.COFFEE_BEAN_PACKAGE,
            1000L,
            3
    );

    private static Order newOrder = new Order(
            UUID.randomUUID(),
            new Email("test@naver.com"),
            "강원특별자치도",
            "244444",
            List.of(newOrderItem),
            OrderStatus.ACCEPTED,
            LocalDateTime.now(),
            LocalDateTime.now()
    );

    @Test
    @DisplayName("주문을 추가할 수 있다.")
    void testInsert() {
        var inserted = repository.insert(newOrder);
        assertThat(inserted, samePropertyValuesAs(newOrder));
    }

}